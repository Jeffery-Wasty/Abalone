import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class MoveHistory extends VBox {

    public static final String[] BOARD_NOTATION = new String[]{
            "I5", "I6", "I7", "I8", "I9",
            "H4", "H5", "H6", "H7", "H8", "H9",
            "G3", "G4", "G5", "G6", "G7", "G8", "G9",
            "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9",
            "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9",
            "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8",
            "C1", "C2", "C3", "C4", "C5", "C6", "C7",
            "B1", "B2", "B3", "B4", "B5", "B6",
            "A1", "A2", "A3", "A4", "A5",
    };

    public static final String[] DIRECTION_NOTATION = new String[] {
            "←", "↖", "↗", "→", "↘", "↙"
    };

    private final Label historyLabel = new Label("Move History: ");
    private final ListView<String> historyListView = new ListView<>();
    private final ObservableList<String> history = FXCollections.observableArrayList();

    public MoveHistory() {
        historyListView.setItems(history);
        historyListView.setPrefSize(100, 300);

        getChildren().addAll(historyLabel, historyListView);
    }

    void addHistory(AbaloneAction action, AbaloneGame.State state) {
        StringBuilder sb = new StringBuilder(history.size() + 1 + ". ");

        if (action.numberOfMarbles == 1) {
            sb.append(BOARD_NOTATION[action.location]);
        } else {
            sb.append(BOARD_NOTATION[action.location]);
            byte[] friendDirections = AbaloneGame.SIDE_MOVE_DIRECTION[action.direction];
            byte direction;
            if (state.getBoard()[AbaloneGame.LOCATION_LOOKUP_TABLE[action.location][friendDirections[0]]] != AbaloneGame.EMPTY) {
                direction = friendDirections[0];
            } else {
                direction = friendDirections[1];
            }
            byte loc = action.location;
            for (int i = 0; i < action.numberOfMarbles - 1; i++) {
                loc = AbaloneGame.LOCATION_LOOKUP_TABLE[loc][direction];
            }
            sb.append('-');
            sb.append(BOARD_NOTATION[loc]);
        }

        sb.append(' ');
        sb.append(DIRECTION_NOTATION[action.direction]);

        history.add(sb.toString());
    }

    void undo() {
        history.remove(history.size() - 1);
    }

}
