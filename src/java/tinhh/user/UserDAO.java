/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tinhh.user;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import tinhh.connection.MyConnection;

/**
 *
 * @author No
 */
public class UserDAO implements Serializable {

    public UserDTO checkLogin(String email, String password) throws Exception {
        MyConnection mcn = new MyConnection();
        String sql = "Select email,password,fullName,sex,phoneNumber,address,createDate,role,status,verifyCode \n"
                + "from tblUser \n"
                + "where email=? and password=?";
        UserDTO dto = new UserDTO();
        try (Connection conn = mcn.makeConnection();
                PreparedStatement preStm = conn.prepareStatement(sql)) {
            preStm.setString(1, email);
            preStm.setString(2, password);
            try (ResultSet rs = preStm.executeQuery()) {
                if (rs.next()) {
                    dto.setFullName(rs.getString("fullName"));
                    dto.setSex(rs.getBoolean("sex"));
                    dto.setPhoneNumber(rs.getString("phoneNumber"));
                    dto.setAddress(rs.getString("address"));
                    dto.setCreateDate(rs.getDate("createDate"));
                    dto.setRole(rs.getString("role"));
                    dto.setStatus(rs.getBoolean("status"));
                    dto.setEmail(email);
                    dto.setPassword(password);
                    dto.setVerifyCode(rs.getString("verifyCode"));
                } else {
                    dto.setRole("invalid");
                }
            }
        }
        return dto;
    }
    
    public boolean checkVerify(String email,String verify) throws Exception{
        MyConnection mcn = new MyConnection();
        String sql = "Select email from tblUser where email=? and verifyCode=?";
        try (Connection conn = mcn.makeConnection();
                PreparedStatement preStm = conn.prepareStatement(sql)) {
            preStm.setString(1, email);
            preStm.setString(2, verify);
            try (ResultSet rs = preStm.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean changeStatus(String email) throws Exception {
        MyConnection mcn = new MyConnection();
        String sql = "Update tblUser set status=1 where email=?";
        try (Connection conn = mcn.makeConnection();
                PreparedStatement preStm = conn.prepareStatement(sql)) {
            preStm.setString(1, email);
            if (preStm.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }
    
        public boolean updateVerify(String verify,String email) throws Exception {
        MyConnection mcn = new MyConnection();
        String sql = "Update tblUser set verifyCode=? where email=?";
        try (Connection conn = mcn.makeConnection();
                PreparedStatement preStm = conn.prepareStatement(sql)) {
            preStm.setString(1, verify);
            preStm.setString(2, email);
            if (preStm.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }
}
