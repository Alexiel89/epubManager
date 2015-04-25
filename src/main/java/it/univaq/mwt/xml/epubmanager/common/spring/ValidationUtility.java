package it.univaq.mwt.xml.epubmanager.common.spring;

import org.springframework.validation.Errors;

/**
 *
 * @author Sonia
 */
public class ValidationUtility {

    public static void rejectIfMaxLength(Errors errors, String fieldName, String errorMessage, String fieldValue, int maxlength) {
        if (fieldValue != null && fieldValue.length() > maxlength) {
            Object[] args = {maxlength};
            errors.rejectValue(fieldName, errorMessage, args, "error.maxlength");
        }
    }

    public static void rejectIfNan(Errors errors, String fieldName,
            String errorMessage, String fieldValue) {

        try {
            long number = Long.parseLong(fieldValue);
        } catch (NumberFormatException e) {
            errors.rejectValue(fieldName, errorMessage, "error.integer");
        }

    }

    public static void rejectIfMinLength(Errors errors, String fieldName, String errorMessage, String fieldValue, int minLength) {
        if (fieldValue != null && fieldValue.length() < minLength) {
            Object[] args = {minLength};
            errors.rejectValue(fieldName, errorMessage, args, "errors.minlength");
        }

    }

    public static void rejectIfAccented(Errors errors, String fieldName,
            String errorMessage, String fieldValue) {
        if (fieldValue.matches("(.*)[à|è|é|ì|ò|ù](.*)")) {

            errors.rejectValue(fieldName, errorMessage, "errors.accent");

        }
    }

    public static void rejectIfNal(Errors errors, String fieldName,
            String errorMessage, String fieldValue) {

        if (!(fieldValue.matches("it|en|es|fr"))) {

            errors.rejectValue(fieldName, errorMessage, "errors.linguedisponibili");

        }

    }
}
