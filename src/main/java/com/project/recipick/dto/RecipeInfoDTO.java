package com.project.recipick.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RecipeInfoDTO {
    @JsonProperty("Grid_20150827000000000227_1")
    private GridResponse grid;

    // getter, setter
    public GridResponse getGrid() { return grid; }
    public void setGrid(GridResponse grid) { this.grid = grid; }
}

class GridResponse {
    private int totalCnt;
    private int startRow;
    private int endRow;
    private Result result;
    private List<Row> row;

    // getter, setter
    public int getTotalCnt() { return totalCnt; }
    public void setTotalCnt(int totalCnt) { this.totalCnt = totalCnt; }

    public int getStartRow() { return startRow; }
    public void setStartRow(int startRow) { this.startRow = startRow; }

    public int getEndRow() { return endRow; }
    public void setEndRow(int endRow) { this.endRow = endRow; }

    public Result getResult() { return result; }
    public void setResult(Result result) { this.result = result; }

    public List<Row> getRow() { return row; }
    public void setRow(List<Row> row) { this.row = row; }
}

class Result {
    private String code;
    private String message;

    // getter, setter
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

class Row {
    @JsonProperty("ROW_NUM")
    private int rowNum;

    @JsonProperty("RECIPE_ID")
    private int recipeId;

    @JsonProperty("IRDNT_SN")
    private int irdntSn;

    @JsonProperty("IRDNT_NM")
    private String irdntNm;

    @JsonProperty("IRDNT_CPCTY")
    private String irdntCpcty;

    @JsonProperty("IRDNT_TY_CODE")
    private String irdntTyCode;

    @JsonProperty("IRDNT_TY_NM")
    private String irdntTyNm;

    // getter, setter
    public int getRowNum() { return rowNum; }
    public void setRowNum(int rowNum) { this.rowNum = rowNum; }

    public int getRecipeId() { return recipeId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

    public int getIrdntSn() { return irdntSn; }
    public void setIrdntSn(int irdntSn) { this.irdntSn = irdntSn; }

    public String getIrdntNm() { return irdntNm; }
    public void setIrdntNm(String irdntNm) { this.irdntNm = irdntNm; }

    public String getIrdntCpcty() { return irdntCpcty; }
    public void setIrdntCpcty(String irdntCpcty) { this.irdntCpcty = irdntCpcty; }

    public String getIrdntTyCode() { return irdntTyCode; }
    public void setIrdntTyCode(String irdntTyCode) { this.irdntTyCode = irdntTyCode; }

    public String getIrdntTyNm() { return irdntTyNm; }
    public void setIrdntTyNm(String irdntTyNm) { this.irdntTyNm = irdntTyNm; }
}
