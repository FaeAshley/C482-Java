module main.c492_515 {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.c492_515 to javafx.fxml;
    exports main.c492_515;
    exports main.c492_515.Controller;
    opens main.c492_515.Controller to javafx.fxml;
    exports main.c492_515.Model;
    opens main.c492_515.Model to javafx.fxml;
}