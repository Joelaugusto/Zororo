package sample.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class MessageUtil {

    public MessageUtil(){

    }

    public void showMessage(StackPane stackPane, String message){
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text("Mensagem"));
        jfxDialogLayout.setBody(new Text(message));

        JFXButton jfxButton = new JFXButton("Fechar");

        jfxDialogLayout.setActions(jfxButton);

        JFXDialog jfxDialog = new JFXDialog(stackPane,jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        jfxDialog.show();
        jfxButton.setOnAction(e-> jfxDialog.close());
    }

    public void showConfirmMessage(StackPane stackPane, String message){
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text("Mensagem"));
        jfxDialogLayout.setBody(new Text(message));

        JFXButton buttonYes = new JFXButton("Sim");
        JFXButton buttonNo = new JFXButton("No");

        jfxDialogLayout.setActions(buttonYes, buttonNo);

        JFXDialog jfxDialog = new JFXDialog(stackPane,jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        jfxDialog.show();

        buttonYes.setOnAction(e->jfxDialog.close());
        buttonNo.setOnAction(e-> jfxDialog.close());

    }
}
