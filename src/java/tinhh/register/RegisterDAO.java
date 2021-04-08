/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tinhh.register;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import tinhh.connection.MyConnection;

/**
 *
 * @author DELL
 */
public class RegisterDAO implements Serializable {

    public boolean checkExistAccount(String email) throws Exception {
        MyConnection mcn = new MyConnection();
        String sql = "Select email\n"
                + "from tblUser\n"
                + "where email=?";
        try (Connection conn = mcn.makeConnection();
                PreparedStatement preStm = conn.prepareStatement(sql)) {
            preStm.setString(1, email);
            try (ResultSet rs = preStm.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean registerAccount(String email, String password, String fullName, boolean sex, String phoneNumber,String address, String role, boolean status,String verifyCode) throws Exception {
        MyConnection mcn = new MyConnection();
        String sql = "INSERT INTO tblUser(email,password,fullName,sex,phoneNumber,address,createDate,role,status,verifyCode)\n"
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = mcn.makeConnection();
                PreparedStatement preStm = conn.prepareStatement(sql)) {
            preStm.setString(1, email);
            preStm.setString(2, password);
            preStm.setString(3, fullName);
            preStm.setBoolean(4, sex);
            preStm.setString(5, phoneNumber);
            preStm.setString(6, address);
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            preStm.setTimestamp(7, ts);
            preStm.setString(8, role);
            preStm.setBoolean(9, status);
            preStm.setString(10, verifyCode);
            if (preStm.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }
}
