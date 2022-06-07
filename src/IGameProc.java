public interface IGameProc {
    void GameStart();
    void Turn();
    void GameFinish(boolean result);
    void ShowCards();
}
