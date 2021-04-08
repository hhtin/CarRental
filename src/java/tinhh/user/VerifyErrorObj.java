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
public class VerifyErrorObj implements Serializable{
    private String verifyError;

    public VerifyErrorObj() {
    }

    public VerifyErrorObj(String verifyError) {
        this.verifyError = verifyError;
    }

    public String getVerifyError() {
        return verifyError;
    }

    public void setVerifyError(String verifyError) {
        this.verifyError = verifyError;
    }
    
    
}
