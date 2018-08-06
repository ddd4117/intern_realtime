package com.example.demo.dao;

import com.example.demo.common.item.GPS;
import com.example.demo.common.item.daoclass.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NodeDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<String> getNodeID(GPS gps) {
        double minX = gps.getX() - 0.003;
        double maxX = gps.getX() + 0.003;
        double minY = gps.getY() - 0.003;
        double maxY = gps.getY() + 0.003;
        String query = "select node_id from node.node_info i where i.Y > " + minY + " and i.Y < " + maxY + " and i.X > " + minX + " and i.X < " + maxX;
//        System.out.println(query);
        List<String> stringList = jdbcTemplate.query(query, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(1);
            }
        });
        return stringList;
    }

    public List<Node> getNode() {
        String query = "select * from node.node_info";
        List<Node> nodeList = jdbcTemplate.query(query, new RowMapper<Node>() {
            @Override
            public Node mapRow(ResultSet rs, int rowNum) throws SQLException {
                Node node = new Node();
                node.setNode_id(rs.getString(1));
                node.setX(rs.getDouble(2));
                node.setY(rs.getDouble(3));
                node.setNode_type(rs.getInt(4));
                node.setNode_name(rs.getString(5));
                node.setStnl_reg(rs.getInt(6));
                return node;
            }
        });
        for (Node n : nodeList) {
            System.out.println(n.toString());
        }
        return nodeList;
    }
}
