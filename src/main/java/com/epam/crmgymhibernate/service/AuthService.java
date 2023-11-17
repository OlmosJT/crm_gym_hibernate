package com.epam.crmgymhibernate.service;

import com.epam.crmgymhibernate.dto.request.ChangePasswordRequest;
import com.epam.crmgymhibernate.dto.request.RegisterTraineeRequest;
import com.epam.crmgymhibernate.dto.request.RegisterTrainerRequest;
import com.epam.crmgymhibernate.dto.response.RegisterResponse;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import static org.passay.IllegalCharacterRule.ERROR_CODE;

public interface AuthService {
    RegisterResponse registerTrainee(RegisterTraineeRequest request);
    RegisterResponse registerTrainer(RegisterTrainerRequest request);

    void changeUserPassword(ChangePasswordRequest request);

    default String generatePassword(int length) {
        PasswordGenerator generator = new PasswordGenerator();

        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        return generator.generatePassword(length, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
    }

}
