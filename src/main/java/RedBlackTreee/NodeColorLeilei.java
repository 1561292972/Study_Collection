package RedBlackTreee;

/**
 * @author leilei
 * @description: node节点颜色 红 黑
 * @date 2020-01-15 17:17
 */
public enum  NodeColorLeilei {

    /**
     *红色
     */
    red(1,"red"),

    /**
     *黑色
     */
    black(2,"black");


    int color;
    String desc;
    NodeColorLeilei(int color, String desc) {
        this.color=color;
        this.desc=desc;
    }
}
