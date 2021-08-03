package com.ssafy.d204.chat.handler;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(java.util.ArrayList.class)

@MappedJdbcTypes(JdbcType.ARRAY)

public class CustomTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(
                    PreparedStatement psmt, int columnIndex, List<String> list, JdbcType jdbcType
                    ) throws SQLException {
        if (list == null || list.size() == 0) {
            psmt.setArray(columnIndex, null);
            return;
        }

        String[] datum = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            datum[i] = list.get(i);
        }
        psmt.setArray(columnIndex, psmt.getConnection().createArrayOf("varchar", datum));
    }
    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getArrayListFromSqlArray(rs.getArray(columnName));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getArrayListFromSqlArray(rs.getArray(columnIndex));
    }

    private List<String> getArrayListFromSqlArray(Array array) throws SQLException {
        if (array == null) {
            return null;
        }

        String[] datum = (String[])array.getArray();
        if (datum == null) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < datum.length; i++) {
            list.add(datum[i]);
        }
        return list;
    }
	@Override
	public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return getArrayListFromSqlArray(cs.getArray(columnIndex));
	}
}

