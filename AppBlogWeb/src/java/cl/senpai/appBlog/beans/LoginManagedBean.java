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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

/**
 *
 * @author Senpai
 */
@Named(value = "loginManagedBean")
@SessionScoped
public class LoginManagedBean implements Serializable {

    @Inject
    private UsuariosDAOLocal usuarioDAO;
    private Usuario usuarioLogeado;
    private String correo;
    private String contrasena;
    
    public LoginManagedBean() {
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

    public Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(Usuario usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }
      
    public void iniciarSesion(ActionEvent e) throws Exception{
        
        String hash = PasswordUtil.generateSecurePassword(contrasena, UtilsConstants.salt);
        this.usuarioLogeado = this.usuarioDAO.findUsuarioByCorreoAndPassword(correo, hash);
        
        if(this.usuarioLogeado != null){
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Contraseña o usuario invalidos"));    
        }
        
    }
    
    public void cerrarSesion(ActionEvent e) throws Exception{
        
        this.usuarioLogeado = null;
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        
    }
}
