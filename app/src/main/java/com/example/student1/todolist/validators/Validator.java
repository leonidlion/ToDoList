package com.example.student1.todolist.validators;


import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Validator<T> {
    private Set<Rule<T>> rules;
    private ArrayList<String> messages;

    private abstract static class Rule<T>{
        protected String message;

        public Rule(String message) {
            this.message = message;
        }

        public abstract boolean validate(T value);
    }

    private abstract static class CriteriaRule<T, C> extends Rule<T>{
        protected C criteria;

        public CriteriaRule(String message, C criteria) {
            super(message);
            this.criteria = criteria;
        }
    }

    private Validator() {
        rules = new HashSet<>();
        messages = new ArrayList<>();
    }

    public @Nullable String getLastMessage(){
        return messages.isEmpty() ? null : messages.get(messages.size() - 1);
    }

    public boolean validate(T value, String ... filedName){
        boolean result = true;
        messages.clear();
        for (Rule<T> rule : rules){
            if (!rule.validate(value)){
                result = false;
                String message = rule.message;
                if (filedName != null && filedName.length > 0){
                    message = String.format("%s %s", filedName[0], rule.message);
                }
                messages.add(message);
            }
        }
        return result;
    }

    public static class StringValidatorBuilder {
        Validator<String> validator;
        private Rule<String> notEmptyRule;
        private Rule<String> notEmptyOrWhiteSpaces;
        private CriteriaRule<String, Integer> minLengthRule;

        private Rule<String> getNotEmptyRule(String ... messages){
            String message = getMessageString(messages, "cannot be blank");
            if (notEmptyRule != null){
                notEmptyRule.message = message;
            }else {
                notEmptyRule = new Rule<String>(message) {
                    @Override
                    public boolean validate(String value) {
                        return !TextUtils.isEmpty(value);
                    }
                };
            }
            return notEmptyRule;
        }

        private Rule<String> getNotEmptyOrWhiteSpaces(String ... messages){
            String message = getMessageString(messages, "cannot be blank");
            if (notEmptyOrWhiteSpaces != null){
                notEmptyOrWhiteSpaces.message = message;
            }else {
                notEmptyOrWhiteSpaces = new Rule<String>(message) {
                    @Override
                    public boolean validate(String value) {
                        return !TextUtils.isEmpty(value) && !TextUtils.isEmpty(value.trim());
                    }
                };
            }
            return notEmptyOrWhiteSpaces;
        }

        private CriteriaRule<String ,Integer> getMinLengthRule(Integer value, String...messages){
            String message = getMessageString(messages, String.format(Locale.getDefault(), "must contain more than %d symbols", value));
            if (minLengthRule != null){
                minLengthRule.message = message;
            }else {
                minLengthRule = new CriteriaRule<String, Integer>(message, value) {
                    @Override
                    public boolean validate(String value) {
                        return !TextUtils.isEmpty(value) && value.length() >= this.criteria;
                    }
                };
            }
            return minLengthRule;
        }

        private String getMessageString(String[] messages, String defaultValue) {
            String message;
            if (messages == null || messages.length == 0){
                message = defaultValue;
            }else {
                message = messages[0];
            }
            return message;
        }

        public StringValidatorBuilder(){
            validator = new Validator<String>();
        }

        public StringValidatorBuilder setNotEmpty(String ... message){
            validator.rules.add(getNotEmptyRule(message));
            return this;
        }

        public StringValidatorBuilder setNotEmptyOrWhiteSpaces(String...message){
            validator.rules.add(getNotEmptyOrWhiteSpaces(message));
            return this;
        }

        public StringValidatorBuilder setMinLength(int minValue, String ... message){
            validator.rules.add(getMinLengthRule(minValue, message));
            return this;
        }

        public Validator<String> build(){
            return validator;
        }
    }

    /**
     *
     */
    public static class NumberValidatorBuilder<N extends Number> {
        Validator<N> validator;
        private CriteriaRule<N, N> minValueRule;
        private CriteriaRule<N, N> maxValueRule;

        public NumberValidatorBuilder(){
            validator = new Validator<>();
        }

        private CriteriaRule<N, N> getMinValueRule(N value){
            String message = String.format(Locale.getDefault(), "must more than %f", value.doubleValue());
            if (minValueRule != null){
                minValueRule.message = message;
            }else {
                minValueRule = new CriteriaRule<N, N>(message, value) {
                    @Override
                    public boolean validate(N value) {
                        return value.doubleValue() > this.criteria.doubleValue();
                    }
                };
            }
            return minValueRule;
        }

        private CriteriaRule<N, N> getMaxValueRule(N value){
            String message = String.format(Locale.getDefault(), "must less than %f", value.doubleValue());
            if (maxValueRule != null){
                maxValueRule.message = message;
            }else {
                maxValueRule = new CriteriaRule<N, N>(message, value) {
                    @Override
                    public boolean validate(N value) {
                        return value.doubleValue() < this.criteria.doubleValue();
                    }
                };
            }
            return maxValueRule;
        }

        public NumberValidatorBuilder setMinValue (N minValue){
            N value = minValue;
            if (maxValueRule != null){
                if (maxValueRule.criteria.doubleValue() < minValue.doubleValue()){
                    value = maxValueRule.criteria;
                    maxValueRule.criteria = minValue;
                }
            }
            validator.rules.add(getMinValueRule(value));
            return this;
        }

        public NumberValidatorBuilder setMaxValue(N maxValue){
            N value = maxValue;
            if (minValueRule != null){
                if (minValueRule.criteria.doubleValue() > maxValue.doubleValue()){
                    value = minValueRule.criteria;
                    minValueRule.criteria = maxValue;
                }
            }
            validator.rules.add(getMaxValueRule(value));
            return this;
        }

        public NumberValidatorBuilder setRange(N minValue, N maxValue){
            N min = minValue, max = maxValue;
            if (minValue.doubleValue() > maxValue.doubleValue()){
                min = maxValue;
                max = minValue;
            }

            validator.rules.add(getMinValueRule(min));
            validator.rules.add(getMaxValueRule(max));
            return this;
        }

        public Validator<N> build(){
            return validator;
        }
    }

    //TODO: Validator for date
    public static class DateValidatorBuilder {
        private Validator<Date> validator;
        private CriteriaRule<Date, String> dateCriterianRule;

        public DateValidatorBuilder(){
            validator = new Validator<>();
        }


        public DateValidatorBuilder setDate(long time) {
            String message = "Error";

            return null;
        }

        public CriteriaRule<Date, String> getDateCriterianRule(Date date){
            return null;
        }

        public Validator<Date> build(){
            return validator;
        }
    }
}
