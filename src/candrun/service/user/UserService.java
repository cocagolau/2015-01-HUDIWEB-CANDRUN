package candrun.service.user;

import java.security.PrivateKey;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import candrun.dao.UserDAO;
import candrun.model.User;
import candrun.service.security.SecurityService;
import candrun.support.enums.CommonError;
import candrun.support.enums.CommonInvar;
import candrun.support.enums.Security;
import candrun.support.enums.UserErrorcode;

public class UserService {
	private static final Logger logger = LoggerFactory
			.getLogger(UserService.class);

	private UserDAO userDAO;

	public UserService(UserDAO userDao) {
		this.userDAO = userDao;
	}

	public String login(String eEmail, String ePw, HttpSession session)
			throws Exception {
		User user;
		int userState;
		String email;
		String pw;
		PrivateKey privateKey = (PrivateKey) session
				.getAttribute(Security.RSA_PRI_KEY.getValue());

		email = SecurityService.decrytRsa(privateKey, eEmail);
		pw = SecurityService.decrytRsa(privateKey, ePw);

		try {
			user = userDAO.findByEmail(email);
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.toString());
			return UserErrorcode.EMPTY.getValue();
		}
		if (!(user.getPassword().equals(SecurityService.encrypt(pw, pw)))) {
			return UserErrorcode.WRONG_PW.getValue();
		}

		userState = user.getState();
		if (userState == 0) {
			return UserErrorcode.NOT_YET_CERTI.getValue();
		}
		if (userState == 1) {
			session.setAttribute(email, email);
			return CommonInvar.SUCCESS.getValue();
		}
		return CommonInvar.DEFAULT.getValue();
	}

	public String register(String eEmail, String nick, String ePw,
			HttpSession session) {
		String email;
		String pw;
		PrivateKey privateKey = (PrivateKey) session
				.getAttribute(Security.RSA_PRI_KEY.getValue());
		try {
			email = SecurityService.decrytRsa(privateKey, eEmail);
			pw = SecurityService.decrytRsa(privateKey, ePw);
			userDAO.addUser(new User(email, nick, SecurityService.encrypt(pw, pw)));
		} catch (DuplicateKeyException e) {
			logger.error(e.toString());
			return UserErrorcode.DUP.getValue();
		} catch (Exception e) {
			logger.error(e.toString());
			return CommonError.SERVER.getValue();
		}
		return CommonInvar.SUCCESS.getValue();
	}

	// public void setRAS(HttpSession session, Model model)
	// throws GeneralSecurityException {
	// KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	// keyPairGenerator.initialize(1024);
	// KeyPair keyPair = keyPairGenerator.generateKeyPair();
	// PublicKey publicKey = keyPair.getPublic();
	// PrivateKey privateKey = keyPair.getPrivate();
	//
	// String pubKey = Base64.encode(publicKey.getEncoded());
	// String priKey = Base64.encode(privateKey.getEncoded());
	//
	// logger.debug("PublicKey:" + pubKey);
	// logger.debug("PrivateKey:" + priKey);
	//
	// session.setAttribute(Security.RSA_PRI_KEY.getValue(), privateKey);
	// model.addAttribute(Security.RSA_PUB_KEY.getValue(), pubKey);
	// }

	// public String decrytRsa(PrivateKey privateKey, String encryptedValue)
	// throws Exception {
	// Cipher cipher = Cipher.getInstance("RSA");
	// byte[] decryptedBytes = null;
	// byte[] encryptedBytes = Base64.decode(encryptedValue);
	//
	// cipher.init(Cipher.DECRYPT_MODE, privateKey);
	// decryptedBytes = cipher.doFinal(encryptedBytes);
	// return new String(decryptedBytes, "utf-8");
	// }
	//
	//
}
