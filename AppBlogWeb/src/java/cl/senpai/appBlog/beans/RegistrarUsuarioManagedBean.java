/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.senpai.appBlog.beans;

import cl.senpai.appBlog.dao.UsuariosDAOLocal;
import cl.senpai.appBlog.dto.Usuario;
import cl.senpai.appBlog.utils.PasswordUtil;
import cl.senpai.appBlog.utils.UtilsConstants;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Senpai
 */
@Named(value = "registrarUsuarioManagedBean")
@ViewScoped
public class RegistrarUsuarioManagedBean implements Serializable {

    @Inject
    private UsuariosDAOLocal usuariosDAO;
    private String nombre;
    private String correo;
    private String contrasena;

    public RegistrarUsuarioManagedBean() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void registrar(ActionEvent e) {

        if (validarCorreoExistente(correo)) {
       
            String hash = PasswordUtil.generateSecurePassword(contrasena, UtilsConstants.salt);

            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setCorreo(correo);
            u.setContrasena(hash);
            u.setEstado(1);

            usuariosDAO.add(u);
         
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Registrado!, puedes logearte"));

        } else {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Correo ya registrado, ingrese otro"));

        }
    }

    public boolean validarCorreoExistente(String correo) {

        Usuario u = usuariosDAO.findUsuarioByCorreo(correo);
        return u == null;
    }
}
