package fr.sciforma.apietnic.service;

import com.sciforma.psnext.api.PSException;
import com.sciforma.psnext.api.Project;
import com.sciforma.psnext.api.Session;
import lombok.Getter;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mehdi
 */
@Getter
@Configuration
@PropertySource("file:${user.dir}/config/application.properties")
public class SciformaService {
    
    @Value("${url}")
    private String url;
    @Value("${context}")
    private String context;
    @Value("${username}")
    private String username;
    @Value("${password}")
    private String password;
   
    private Session session;

    public boolean createConnection() {

        try {

            Logger.info("Connection to " + url + "/" + context + " with username " + username);
            session = new Session(url + "/" + context);
            session.login(username, password.toCharArray());
            Logger.info("Connection successful");
            
            return true;

        } catch (PSException e) {
            Logger.error("Failed to connect to sciforma : " + e.getMessage() , e);
        }
        
        return false;

    }

    public void closeConnection() {

        try {

            if (session.isLoggedIn()) {
                session.logout();
                Logger.info("Logout successful");
            }

        } catch (PSException e) {
            Logger.error("Failed to logout", e);
        }

    }
    
    public List<Project> getProjectList(int version, int access) {

        if (session.isLoggedIn()) {

            try {

                return session.getProjectList(version, access);

            } catch (PSException e) {

                Logger.error("Failed to retrieve project list", e);

            }

        } else {
            
            Logger.error("You must be logged in to retrieve project list");
            
        }

        return new ArrayList<>();

    }
}
