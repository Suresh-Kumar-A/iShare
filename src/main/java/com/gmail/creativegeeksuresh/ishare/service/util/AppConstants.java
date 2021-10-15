package com.gmail.creativegeeksuresh.ishare.service.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AppConstants {

    // Library Staff roles - Librarian
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_GUEST = "USER";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final Set<String> DEFAULT_ROLE_SET = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(ROLE_ADMIN, ROLE_USER, ROLE_GUEST)));
}
