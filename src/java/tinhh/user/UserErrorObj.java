/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tinhh.user;

import java.io.Serializable;

/**
 *
 * @author No
 */
public class UserErrorObj implements Serializable{
    private String usernameError,passwordError,catchaError;

    public UserErrorObj() {
    }

    public UserErrorObj(String usernameError, String passwordError, String catchaError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.catchaError = catchaError;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getCatchaError() {
        return catchaError;
    }

    public void setCatchaError(String catchaError) {
        this.catchaError = catchaError;
    }

   
    
}
