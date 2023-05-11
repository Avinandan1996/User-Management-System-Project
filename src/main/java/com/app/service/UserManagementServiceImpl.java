package com.app.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.binding.LoginForm;
import com.app.binding.UnlockAccountForm;
import com.app.binding.UserForm;
import com.app.entity.CityMaster;
import com.app.entity.CountryMaster;
import com.app.entity.StateMaster;
import com.app.entity.User;
import com.app.repository.CityRepository;
import com.app.repository.CountryRepository;
import com.app.repository.StateRepository;
import com.app.repository.UserRepository;
import com.app.utils.EmailUtils;

@Service
public class UserManagementServiceImpl implements UserMgmtService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private StateRepository stateRepo;

	@Autowired
	private CityRepository cityRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String checkEmail(String email) {

		User user = userRepo.findByEmail(email);

		if (user == null) {
			return "Unique";
		}
		return "Duplicate";
	}

	@Override
	public Map<Integer, String> getCountries() {

		List<CountryMaster> countries = countryRepo.findAll();

		Map<Integer, String> countryMap = new HashMap<>();

		countries.forEach(country -> {
			countryMap.put(country.getCountryId(), country.getCountryName());
		});

		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {

		List<StateMaster> states = stateRepo.findByCountryId(countryId);

		Map<Integer, String> stateMap = new HashMap<>();

		states.forEach(state -> {
			stateMap.put(state.getStateId(), state.getStateName());
		});

		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer cityId) {

		List<CityMaster> cities = cityRepo.findByStateId(cityId);

		Map<Integer, String> cityMap = new HashMap<>();

		cities.forEach(city -> {
			cityMap.put(city.getCityId(), city.getCityName());
		});
		return cityMap;
	}

	@Override
	public String registerUser(UserForm userForm) {

		// copy data from binding obj to entity obj
		User entity = new User();
		BeanUtils.copyProperties(userForm, entity);

		// Generate and set random password
		entity.setUserPwd(generateRandomPwd());

		// set Account status as Locked
		entity.setAccStatus("Locked");

		userRepo.save(entity);

		// send email to unlock account
		String to = userForm.getEmail();
		String subject = "Registration Email";
		String body = readEmailBody("REG_EMAIL_BODY.txt", entity);
		emailUtils.sendMail(to, subject, body);
		

		return "User Account Created";
	}

	@Override
	public String unlockAccount(UnlockAccountForm unlockAccForm) {
		String email = unlockAccForm.getEmail();
		User user = userRepo.findByEmail(email);
		if (user != null && user.getUserPwd().equals(unlockAccForm.getTempPwd())) {
			user.setUserPwd(unlockAccForm.getNewPwd());
			user.setAccStatus("Unlock");
			userRepo.save(user);
			return "Account Unlock";

		}

		return "Invalid Temporary Password";
	}

	@Override
	public String login(LoginForm loginForm) {
		User user = userRepo.findByEmailAndUserPwd(loginForm.getEmail(), loginForm.getPwd());

		if (user == null) {
			return "Invalid Credential";
		}

		if (user.getAccStatus().equals("LOCKED")) {
			return "Account is Locked";

		}
		return "Success";
	}

	@Override
	public String forgotPwd(String email) {

		User user = userRepo.findByEmail(email);

		if (user == null) {
			return "No Account Found";
		}
		// send email to user with pwd

		String subject = "Recover Password";
		String body = readEmailBody("FORGET_PWD_EMAIL_BODY.txt", user);
		emailUtils.sendMail(email, subject, body);
		return null;
	}

	private String generateRandomPwd() {

		String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();

		int pwdLength = 6;

		for (int i = 1; i <= pwdLength; i++) {
			int index = random.nextInt(text.length());
			sb.append(text.charAt(index));
		}
		return sb.toString();
	}

	private String readEmailBody(String filename, User user)  {
		StringBuffer sb = new StringBuffer();
		try (Stream<String> lines = Files.lines(Paths.get(filename))) {
			lines.forEach(line -> {
				line = line.replace("$(FNAME)", user.getFname());
				line = line.replace("$(LNAME)", user.getLname());
				line = line.replace("$(EMAIL)", user.getEmail());
				line = line.replace("$(PWD)", user.getUserPwd());
				line = line.replace("$(TEMP_PWD)", user.getUserPwd());
				sb.append(line);

			});

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();

	}

}
