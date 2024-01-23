package jp.ac.uryukyu.ie.e235723;
/**
 * Cellクラスは、セルの状態を設定するクラス
 */
public class Cell {
    private String wild; 
    /**
     * getter
     * @return String wild 初期表示
     */
    String getWild(){ 
        return wild;
    }

    private String bomb; 
    /**
     * getter
     * @return　String bomb 爆弾
     */
    String getBomb(){ 
        return bomb;
    }

    private boolean isBomb; 
    /**
     * getter
     * @return boolean isBomb 爆弾があるかどうか true = 爆弾がある
     */
    public boolean getIsBomb(){
        return isBomb;
    }
    /**
     * setter 
     * @param isBomb boolean isBomb 爆弾があるかどうか true = 爆弾がある
     */
    public void setIsBomb(boolean isBomb){ 
        this.isBomb = isBomb;
    }

    private boolean isOpen; 
    /**
     * getter
     * @return　boolean isOpen セルが開かれたかどうか true = 開かれた
     */
    public boolean getIsOpen(){
        return isOpen;
    }
 
    /**
     * セルを開くメソッド
     * isOpen = true　にする
     */
    public void open(){ 
        isOpen = true; 
    }

    private int surroundBomb; 
    /**
     * getter
     * @return　int surroundBomb 周囲の地雷の数
     */
    public int getSurroundBomb(){
        return surroundBomb;
    }

    /**
     * setter
     * @param sorroundBomb int sorroundBomb 周囲の地雷の数
     */
    public void setSurroundBomb(int sorroundBomb){ 
        this.surroundBomb = sorroundBomb;
    }

    /**
     * コンストラクタ
     */
    public Cell(){
        this.wild = " - ";
        this.bomb = " * ";
        this.isBomb = false;
        this.isOpen = false;
        this.surroundBomb = 0;
    }

    /**
     * セルに何を表示するか決めるメソッド
     * isOpen == falseならwildを返す
     * isOpen == trueかつisBomb == trueならbombを返す
     * isOpen == trueかつisBomb == falseならsurroundBombをString型にして表示
     */
    public String putBoard(){ 
        if(isOpen == false){ 
            return wild;
        }else{ 
            if(isBomb == true){ 
                return bomb; 
            }
            return " " + String.valueOf(surroundBomb) + " "; 
        }
    }
}

