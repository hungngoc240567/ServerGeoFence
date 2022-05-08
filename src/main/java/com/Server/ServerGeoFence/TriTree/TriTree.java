package com.Server.ServerGeoFence.TriTree;

import com.Server.ServerGeoFence.SupportClass.Edge;
import com.Server.ServerGeoFence.SupportClass.Point;

import java.util.ArrayList;
import java.util.List;

public class TriTree {
    private static final short INIT_SIZE_NODE = 4;
    private static final Point FIRST_QUARTER_POINT = new Point(-1, 0);
    private static final Point SECOND_QUARTER_POINT = new Point(0, 1);
    private static final Point THIRD_QUARTER_POINT = new Point(1,0);
    private static final Point FOURTH_QUARTER_POINT = new Point(0, -1);

    private int maxDepth = 0;
    private int maxNodePerChild = 0;
    private Point originPoint;
    private TriNode tree[] = new TriNode[INIT_SIZE_NODE];
    private List<Edge> polygon;

    private Point getOriginPoint(List<Point> listPoint){
        double originX = 0;
        double originY = 0;
        for (int i = 0;i < listPoint.size();i++){
            originX += listPoint.get(i).getX();
            originY += listPoint.get(i).getY();
        }
        originX /= listPoint.size();
        originY /= listPoint.size();
        return new Point(originX, originY);
    }

    private List<Edge> createListEdge(List<Point> listPoint){
        List<Edge> listEdge = new ArrayList<>();
        for(int i = 0;i < listPoint.size();i++){
            Point p1 = listPoint.get(i).sub(originPoint);
            Point p2 = (i == listPoint.size() - 1) ? listPoint.get(0).sub(originPoint) : listPoint.get(i + 1).sub(originPoint);
            Edge edge = new Edge(p1, p2);
            listEdge.add(edge);
        }
        return listEdge;
    }

    public void build(List<Point> listPoint){
        this.originPoint = this.getOriginPoint(listPoint);
        this.polygon = createListEdge(listPoint);
        this.maxDepth = (int)(Math.log(this.polygon.size()) / Math.log(2)) + 1;
        this.maxNodePerChild = (int)(Math.log(this.polygon.size()) / Math.log(2)) + 1;

        // tao 4 init node tuong ung voi 4 phan cua mat phang
        tree[0] = new TriNode();
        this.buildSubInitNode(this.polygon, FIRST_QUARTER_POINT, SECOND_QUARTER_POINT, tree[0]);
        tree[1] = new TriNode();
        this.buildSubInitNode(this.polygon, SECOND_QUARTER_POINT, THIRD_QUARTER_POINT, tree[1]);
        tree[2] = new TriNode();
        this.buildSubInitNode(this.polygon, THIRD_QUARTER_POINT, FOURTH_QUARTER_POINT, tree[2]);
        tree[3] = new TriNode();
        this.buildSubInitNode(this.polygon, FOURTH_QUARTER_POINT, FIRST_QUARTER_POINT, tree[3]);
//        for(int i = 0;i < 4;i++){
//            tree[i].print(1);
//        }
    }

    private void buildSubInitNode(List<Edge> listEdge, Point pLeft, Point pRight, TriNode subNode){
        subNode.buildAllSubNode(listEdge, pLeft, pRight, 1, this.maxDepth, this.maxNodePerChild);
    }

    public Boolean isPointInPolygon(Point p){
        Point convertPoint = p.sub(this.originPoint);
        for(int i = 0;i < INIT_SIZE_NODE;i++){
            TriNode initSubTree = tree[i];
            if(initSubTree.isPointInNode(convertPoint)){
                return initSubTree.isPointInPolygon(convertPoint);
            }
        }
        return false;
    }

    public List<List<Edge>> getListEdgeByAllNode(){
        List<List<Edge>> listNode = new ArrayList<>();
        for (int i = 0;i < INIT_SIZE_NODE;i++){
            tree[i].getListEdgeByAllNode(listNode, this.originPoint);
        }
        return listNode;
    }

    public Point getOriginPoint() {
        return originPoint;
    }
}
