package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.dataBase.ConnectionFactory;
import sample.dataBase.DataBaseServer;
import sample.util.FicheirosBat;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        final DataBaseServer dataBaseServer = new DataBaseServer();

        //janela que demostra que o programa foi aberto
        abertura(primaryStage);

        //iniciar o SGBD
        new ConnectionFactory().getEntityManager();

        //desligar o servidor quando a janela for fechada
        primaryStage.setOnCloseRequest(e-> dataBaseServer.stop());

        //iniciar o servidor de base de dados (MySQL)
        dataBaseServer.start();

        janelaPrincipal(primaryStage);

        new FicheirosBat().save();
    }

    private void abertura(Stage primaryStage) throws IOException {
        Parent raiz = FXMLLoader.load(getClass().getResource("view/abertura.fxml"));
        primaryStage.setScene(new Scene(raiz, 480, 300));
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("Pressione ESC para sair do modo tela cheia");
        primaryStage.show();
    }

    private void janelaPrincipal(Stage primaryStage) throws IOException {
        primaryStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("view/main.fxml"));
        primaryStage.setTitle("Zororo");
        primaryStage.setScene(new Scene(root, 1124, 540));
        primaryStage.setResizable(true);
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
