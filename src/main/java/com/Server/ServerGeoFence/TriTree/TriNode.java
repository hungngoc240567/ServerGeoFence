package com.Server.ServerGeoFence.TriTree;

import com.Server.ServerGeoFence.SupportClass.Edge;
import com.Server.ServerGeoFence.SupportClass.Graham;
import com.Server.ServerGeoFence.SupportClass.Point;

import java.util.ArrayList;
import java.util.List;

public class TriNode {
    private Point pLeft;
    private Point pRight;
    private List<Edge> listEdgeInNode;
    private int left;
    private int right;
    private Point outSidePoint = null;
    private TriNode nodeLeft = null;
    private TriNode nodeRight = null;

    public TriNode(){
        listEdgeInNode = new ArrayList<>();
    }

    public List<Edge> getListEdgeInNode() {
        return listEdgeInNode;
    }

    public Point getpLeft() {
        return pLeft;
    }

    public Point getpRight() {
        return pRight;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public TriNode getNodeLeft() {
        return nodeLeft;
    }

    public TriNode getNodeRight() {
        return nodeRight;
    }

    public void setpLeft(Point pLeft) {
        this.pLeft = pLeft;
    }

    public void setpRight(Point pRight) {
        this.pRight = pRight;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setNodeLeft(TriNode nodeLeft) {
        this.nodeLeft = nodeLeft;
    }

    public void setNodeRight(TriNode nodeRight) {
        this.nodeRight = nodeRight;
    }

    public Boolean isLeafNode(){
        return nodeRight == null && nodeLeft == null;
    }

    public Boolean isEdgeInNode(Edge edge){
        if(this.pLeft == null || this.pRight == null) return false;
        Point oPoint = new Point(0, 0);
        Boolean isFirstPointIn = !Graham.isLower(oPoint, pLeft, edge.getFirstPoint()) && Graham.isLower(oPoint, pRight, edge.getFirstPoint());
        Boolean isSecondPointIn = !Graham.isLower(oPoint, pLeft, edge.getSecondPoint()) && Graham.isLower(oPoint, pRight, edge.getSecondPoint());
        if(isFirstPointIn || isSecondPointIn) return true;
        else{
            Boolean isPLeftIn = !Graham.isLower(oPoint, edge.getFirstPoint(), pLeft) && Graham.isLower(oPoint, edge.getSecondPoint(), pLeft);
            Boolean isPRightIn = !Graham.isLower(oPoint, edge.getFirstPoint(), pRight) && Graham.isLower(oPoint, edge.getSecondPoint(), pRight);
            if(isPLeftIn && isPRightIn) return true;
            return false;
        }
    }

    public Boolean isPointInNode(Point p){
        Point oPoint = new Point(0, 0);
        return !Graham.isLower(oPoint, pLeft, p) && Graham.isLower(oPoint, pRight, p);
    }

    private Point createOutSidePoint(){
        // set 1 cho truong hop node
        // khong co edge nao nam trong ca
        double maxLength = 1;
        for(int i = 0;i < listEdgeInNode.size();i++){
            Edge edge = listEdgeInNode.get(i);
            Point p1 = edge.getFirstPoint();
            Point p2 = edge.getSecondPoint();
            if(maxLength < Math.abs(p1.getX())) maxLength = Math.abs(p1.getX());
            if(maxLength < Math.abs(p1.getY())) maxLength = Math.abs(p1.getY());
            if(maxLength < Math.abs(p2.getX())) maxLength = Math.abs(p2.getX());
            if(maxLength < Math.abs(p2.getY())) maxLength = Math.abs(p2.getY());
        }
        // lay length gap doi cho chac chan
        // o ben ngoai cac canh cua node nay
        maxLength *= 2;
        maxLength = Math.sqrt(maxLength * maxLength + maxLength * maxLength);
        Point midPoint = pLeft.getMiddlePoint(pRight);
        Point normalizeMidPoint = midPoint.normalize();
        return new Point(normalizeMidPoint.getX() * maxLength, normalizeMidPoint.getY() * maxLength);
    }

    public void buildAllSubNode(List<Edge> listEdge, Point pLeft, Point pRight, int curDepth, int maxDepth, int maxLength){
        this.pLeft = pLeft;
        this.pRight = pRight;

        List<Edge> listEdgeIn = new ArrayList<>();
        for(int i= 0;i < listEdge.size();i++){
            Edge edge = listEdge.get(i);
            if(this.isEdgeInNode(edge)){
                listEdgeIn.add(edge);
            }
        }

        // Kiem tra truong hop node nay da du so diem be hon nguong diem
        // hoac do sau cua cay da vuot qua nguong thi
        // day la node la
        if(listEdgeIn.size() <= maxLength || curDepth >= maxDepth || listEdge.size() == 0){
            // Neu la Node la thi se luu lai thong tin
            // cac canh nam trong no
            this.listEdgeInNode = listEdgeIn;
            this.outSidePoint = this.createOutSidePoint();
            return;
        }

        Point midPoint = pLeft.getMiddlePoint(pRight);
        nodeLeft = new TriNode();
        nodeRight = new TriNode();
        nodeLeft.buildAllSubNode(listEdgeIn, pLeft, midPoint, curDepth + 1, maxDepth, maxLength);
        nodeRight.buildAllSubNode(listEdgeIn, midPoint, pRight, curDepth + 1, maxDepth, maxLength);
    }

    public Boolean isPointInPolygon(Point p){
        if(this.isLeafNode()){
            return rayCasting(p);
        }
        else if(this.nodeLeft.isPointInNode(p)) return this.nodeLeft.isPointInPolygon(p);
        else if(this.nodeRight.isPointInNode(p)) return this.nodeRight.isPointInPolygon(p);
        return false;
    }

    public Boolean rayCasting(Point p){
          Point p1 = outSidePoint;
          Point p2 = p;
          int count = 0;
          for (int i = 0;i < listEdgeInNode.size();i++){
              Edge edge = listEdgeInNode.get(i);
              if(edge.isIntersect(p1, p2)) count++;
          }
          return count % 2 == 1;
    }

    public void print(int depth){
        System.out.println(pLeft.toString());
        System.out.println(pRight.toString());
        System.out.println(listEdgeInNode.size());
        System.out.println(depth);
        System.out.println(listEdgeInNode);
        System.out.println(outSidePoint);
        if(nodeLeft != null)
            nodeLeft.print(depth + 1);
        if(nodeRight != null)
            nodeRight.print(depth + 1);
    }

    // test field
    public List<List<Edge>> getListEdgeByAllNode(List<List<Edge>> listNode, Point originPoint){
        this.getListEdge(listNode, originPoint);
        return listNode;
    }

    public void getListEdge(List<List<Edge>> listNode, Point originPoint){
        if(this.isLeafNode()) {
            List<Edge> listEdge = new ArrayList<>();
            listEdge.add(new Edge(pLeft, pRight)); // e[0] la hai diem cuc cua node
            listEdge.add(new Edge(this.outSidePoint, new Point(0, 0)));
            for(int i = 0;i < this.listEdgeInNode.size();i++){
                Edge edge = this.listEdgeInNode.get(i);
                listEdge.add(new Edge(edge.getFirstPoint().add(originPoint), edge.getSecondPoint().add(originPoint)));
            }
            listNode.add(listEdge);
        }
        else{
            this.nodeLeft.getListEdge(listNode, originPoint);
            this.nodeRight.getListEdge(listNode, originPoint);
        }
    }
}
