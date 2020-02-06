/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.senpai.appBlog.dao;

import cl.senpai.appBlog.dto.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Senpai
 */
@Stateless
public class UsuariosDAO implements UsuariosDAOLocal {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppBlogEJBPU");
       
    @Override
    public void add(Usuario u) {
        
        EntityManager em = emf.createEntityManager();
        try{
            em.persist(u);
        }catch(Exception e){
            
        }finally{
            em.close();
        }
        
    }

    @Override
    public Usuario findUsuarioByCorreoAndPassword(String correo, String contrasena) {
    
        EntityManager em = emf.createEntityManager();
        try{
            return em.createNamedQuery("Usuario.findByCorreoAndContrasena", Usuario.class)
                    .setParameter("correo", correo)
                    .setParameter("contrasena", contrasena)
                    .getSingleResult();
        }catch(Exception e){
            return null;
        }finally{
            em.close();
        }
        
    }

    @Override
    public Usuario findUsuarioByCorreo(String correo) {
    
        EntityManager em = emf.createEntityManager();
        try{
            return em.createNamedQuery("Usuario.findByCorreo", Usuario.class)
                    .setParameter("correo", correo)
                    .getSingleResult();
        }catch(Exception e){
            return null;
        }finally{
            em.close();
        }
        
    }
    
}
