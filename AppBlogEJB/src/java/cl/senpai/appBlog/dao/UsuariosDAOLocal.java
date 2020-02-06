/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.senpai.appBlog.dao;

import cl.senpai.appBlog.dto.Usuario;
import javax.ejb.Local;

/**
 *
 * @author Senpai
 */
@Local
public interface UsuariosDAOLocal {
    
    void add(Usuario u);
    Usuario findUsuarioByCorreoAndPassword(String correo, String contrasena);
    Usuario findUsuarioByCorreo(String correo);
}
