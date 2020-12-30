package View;

import Model.User;
import Service.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableHandler implements Initializable {

    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, Integer> id;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableColumn<User, String> last_name;
    @FXML
    private TableColumn<User, Integer> age;

    ObservableList<User> lst = FXCollections.observableArrayList(

        new User(0, "Jimmy", "Raynor", 20),
        new User(1, "Dat", "Tran", 20)

    );
    @Override
    public void initialize (URL url, ResourceBundle rb) {

        id.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        last_name.setCellValueFactory(new PropertyValueFactory<User, String>("last_name"));
        age.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));

        lst.add(new User(2, "Nether", "Oblivion", 18));

        table.setItems(lst);

    }

    public void getUserInformation() {
        DatabaseConnection connectNow = new DatabaseConnection();
    }

}
