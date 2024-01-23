package jp.ac.uryukyu.ie.e235723;

import java.util.Random;
import java.util.Scanner;

/**
 *  MineSweeperクラスは、マインスイーパーを実装するためのクラス
 */
public class MineSweeper {
    private int size;
    /**
     * getter
     * @return int size 1列(1行)のセルの数
     */
    public int getSize(){
        return size;
    } 

    private int numBomber;
    /**
     * getter
     * @return　int numBomber 爆弾の数
     */
    public int getNumBomber(){
        return numBomber;
    } 
    
    private Cell[][] cells;
    /**
     * getter
     * @return Cell[][] cells ボードの大きさ
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * scannerを使えるようにする
     */
    Scanner scanner= new Scanner(System.in);

    /**
     * コンストラクタ
     */
    public MineSweeper(){
        this.size = 7; 
        this.numBomber = 10; 
        this.cells = new Cell[size][size];
    }

    /**
     * ボードを初期化するメソッド
     * cellsを使えるようにする
     */
    public void initializeBoard() { 
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    /**
     * ボードを表示するメソッド
     * cells[i][j].putBoard()でそれぞれのセルに適切な記号や数字を表示する
     */
    public void showBoard() { 
        int line = size - 1;
        System.out.println("    0   1   2   3   4   5   6  ");
        System.out.println("  +---+---+---+---+---+---+---+");
         
        for(int i=0; i<size; i++) {
            System.out.print(i + " "); 
            for(int j=0; j<size; j++) {
                System.out.print("|" + cells[i][j].putBoard());

                if(j==line) {
                    System.out.println("|");
                }
                
            }
             
            System.out.println("  +---+---+---+---+---+---+---+");
             
        }    
    }

    /**
     * 指定したセルを開くメソッド
     * cells[row][column].open()で開く
     */
    public void openCell(int row,int column){ 
        cells[row][column].open();
    }

    /**
     * 爆弾をランダムに設置するメソッド
     * allCellsClosed() == trueなら最初に開くセルの行と列を選ばせる
     * ユーザーが選んだ行はfirstOpenRow、列はfirstOpenColumnに保存する
     * 選ばれた数字が0より小さいまたはセルの行や列の数以上の場合再度選ばせる
     * 選んだセルの座標をopenCell(firstOpenRow,firstOpenColumn)で開く
     * 定義した爆弾の数(numBomber)より設置した爆弾の数(countBomb)が小さい間爆弾を設置し続ける
     * 爆弾はユーザーが初めに選んだセルの座標には設置しないよう設定している
     * もしセルに爆弾がまだ置かれてないかつ開かれていないならそのセルに爆弾を設置して、爆弾の数をカウントする
     */
    public void putBomb(){ 
        Random random = new Random();
        int countBomb = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (allCellsClosed() == true) { 
                    showBoard();
                    System.out.println("最初に開くセルの行を選んでください(0~6)");
                    int firstOpenRow = scanner.nextInt();
                    System.out.println("最初に開くセルの列を選んでください(0~6)");
                    int firstOpenColumn = scanner.nextInt();
                    if (firstOpenRow < 0 || firstOpenRow >= size || firstOpenColumn < 0 || firstOpenColumn >= size) {
                        System.out.println("その数字はできません。もう一度選んでください。");
                        continue;
                    }
                    openCell(firstOpenRow,firstOpenColumn);
                }
            }
        }
         
        while(countBomb < numBomber){ 
            int row = random.nextInt(size);
            int column = random.nextInt(size);
            
            if(cells[row][column].getIsBomb() == false && cells[row][column].getIsOpen() == false){
                
                cells[row][column].setIsBomb(true);
                countBomb++;
            }
        }    
    }

    /**
     * 周囲の爆弾の数を計算するメソッド
     * 正しい座標なら爆弾があるかどうかの判断を開始する
     * getIsBomb()メソッドを呼び出してそのセルに爆弾があるか判断する
     */
    public void countSurroundBomb(){ 
        for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
                for(int k = -1; k < 2; k++){
					for(int l = -1; l < 2; l++){
						int x = j + k;
						int y = i + l;
                        if(x >= 0 && x < size && y >= 0 && y < size){ 
                            if(cells[x][y].getIsBomb() == true){ 
                                cells[j][i].setSurroundBomb(cells[j][i].getSurroundBomb() + 1);
                            }
                        }    		
				
			        }
		        }
            }
        }
    } 

    /**
     * ゲームオーバーかどうか判断するメソッド
     * もし爆弾の置かれているセルを開いていたらtrueを返す
     */
    public boolean gameOver(){ 
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[i][j].getIsBomb() == true && cells[i][j].getIsOpen() == true) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * ゲームをクリアしたかどうか判断するメソッド
     * もし爆弾の置かれていないセルが開かれていなかったらfalseを返す
     */
    public boolean gameClear(){ 
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[i][j].getIsBomb() == false && cells[i][j].getIsOpen() == false) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * すべてのセルが閉じているかどうか判断するメソッド
     * cells[i][j].getIsOpen() == trueならfalseを返す
     */
    boolean allCellsClosed() { 
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[i][j].getIsOpen() == true) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 全てのセルを開くメソッド
     * cells[i][j].open()を全てのセルの座標に繰り返して全て開く
     */
    public void openAll(){ 
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j].open();
            }
        }   
    }

    /**
     * ゲームをスタートするメソッド
     * initializeBoard()でボードの初期化
     * putBomb()で爆弾の設置
     * countSurroundBomb()で周囲の爆弾の数を数えて保存
     * breakされるまで無限ループ
     * showBoard()でボードを表示
     * ユーザーに行と列を選んでもらい、それぞれselectRow、selectColumnに保存する
     * 選ばれた数字が0より小さいまたはセルの行や列の数以上の場合再度選ばせる
     * openCell(selectRow,selectColumn)でユーザーが選んだ座標のセルを開く
     * もしgameOver() == trueならopenAll()で全てのセルを開き、showBoard()でボードの表示を行う
     * そしてゲームオーバーの文字を表示してループを抜ける
     * もしgameClear() == trueならopenAll()で全てのセルを開き、showBoard()でボードの表示を行う
     * そしてゲームクリアの文字を表示してループを抜ける9
     * 
     */
    public void gameStart(){
        initializeBoard();
        putBomb();
        countSurroundBomb();  
        while (true) {
            showBoard();
            System.out.println("行を選んでください(0~6)");
            int selectRow = scanner.nextInt();
            System.out.println("列を選んでください(0~6)");
            int selectColumn = scanner.nextInt();
            if (selectRow < 0 || selectRow >= size || selectColumn < 0 || selectColumn >= size) {
                System.out.println("その数字はできません。もう一度選んでください。");
                continue;
            }
            openCell(selectRow,selectColumn);
            if(gameOver() == true){
                openAll();
                showBoard();
                System.out.println("爆発した!ゲームオーバー!");
                break;
            }
            if(gameClear() == true){
                openAll();
                showBoard();
                System.out.println("素晴らしい!ゲームクリア!");
                break;
            }
        }
    }
}

