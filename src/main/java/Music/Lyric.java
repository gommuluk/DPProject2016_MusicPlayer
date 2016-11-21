package Music;

class Lyric {
    private int[][] time;
    private String[] lrc;

    public Lyric(int[][] time, String[] lrc) {
        this.time = time;
        this.lrc = lrc;
    }

    private int timeCal(int min, int sec, int msec) {
        int total;
        total = min * 60 * 100 + sec * 100 + msec;    //0.01초 단위로 표현?
        return total;
    }


}
