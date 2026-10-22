module org.sistemamedicamentos {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.sistemamedicamentos to javafx.fxml;
    opens org.sistemamedicamentos.controller to javafx.fxml;
    opens org.sistemamedicamentos.model to javafx.fxml, javafx.base;

    exports org.sistemamedicamentos;
    exports org.sistemamedicamentos.controller;
    exports org.sistemamedicamentos.model;
}