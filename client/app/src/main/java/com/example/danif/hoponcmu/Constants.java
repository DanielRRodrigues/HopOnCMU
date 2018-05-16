package com.example.danif.hoponcmu;

public final class Constants {

  public static final String LOG_TAG = "HopOnCMU";

  public static final String ERROR_EMPTY_EDIT_TEXT = "This field cannot be empty";

  public static final String TOAST_LOGIN_FAILED = "Login failed";
  public static final String TOAST_LOGIN_SUCCESS = "Logged in with success";
  public static final String TOAST_SIGNUP_FAILED = "Sign up failed";
  public static final String TOAST_SIGNUP_SUCCESS = "Signed up with success";
  public static final String TOAST_DOWNLOAD_QUIZZES_SUCCESS = "Quizzes downloaded";
  public static final String TOAST_DOWNLOAD_QUIZZES_FAILED = "Failed to download quizzes";
  public static final String TOAST_QUIZ_SUBMITED = "Quiz submited";

  public static final String EXTRA_QUIZ = "Quiz";

  public static final int REQUEST_AUTH = 10000;
  public static final int REQUEST_LOGIN = 10001;
  public static final int REQUEST_SIGNUP = 10002;

  public static final int AUTH_OK = 20000;
  public static final int AUTH_FAILED = 20001;
  public static final int LOGIN_OK = 20002;
  public static final int LOGIN_FAILED = 20003;
  public static final int SIGNUP_OK = 20004;
  public static final int SIGNUP_FAILED = 20005;

  private Constants() {
  }
}
