package com.poly.it17326.group2.repository;

import com.poly.it17326.group2.config.HibernateConfig;
import com.poly.it17326.group2.domainmodel.ChucVu;
import com.poly.it17326.group2.domainmodel.NhanVien;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
//hocnvph27417

public class NhanVienRepository {

    private Session session = HibernateConfig.getFACTORY().openSession();
    private Transaction trans;

    public List<NhanVien> getAll() {
        Query query = session.createQuery("FROM TaiKhoan");
        return query.getResultList();
    }

    public List<ChucVu> getChucVu() {
        Query query = session.createQuery("FROM ChucVu");
        return query.getResultList();
    }

    public Boolean add(NhanVien taiKhoan) {
        try ( Session session = HibernateConfig.getFACTORY().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(taiKhoan);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.fillInStackTrace();
            return false;
        }
    }

    public Boolean delete(String id) {
        try ( Session session = HibernateConfig.getFACTORY().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM TaiKhoan WHERE Id = :Id");
            query.setParameter("Id", id);
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public NhanVien getByEmailAndMatKhau(String email, String pass) {
        try {
            Query query = session.createQuery("Select p From NhanVien p where p.email = :email and p.matKhau = :matKhau");
            query.setParameter("email", email);
            query.setParameter("matKhau", pass);
            return (NhanVien) query.getSingleResult();
        } catch (Exception e) {
        }
        return null;
    }

    public NhanVien getByEmail(String email) {
        try {
            Query query = session.createQuery("Select p From NhanVien p where p.email = :email");
            query.setParameter("email", email);
            return (NhanVien) query.getSingleResult();
        } catch (Exception e) {
        }
        return null;
    }

    public Boolean updateMatKhau(String matKhau, String email) {
        try {
            trans = session.beginTransaction();
            String hql = "UPDATE NhanVien SET NHANVIEN.matKhau = :matKhau WHERE NHANVIEN.email = :email";
            Query query = session.createNativeQuery(hql);
            query.setParameter("matKhau", matKhau);
            query.setParameter("email", email);
            query.executeUpdate();
            trans.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        NhanVienRepository nhanVienRepository = new NhanVienRepository();
        nhanVienRepository.updateMatKhau("1234", "nguyentuananh110123@gmail.com");
        //System.out.println(nhanVien.getEmail());
    }
}
