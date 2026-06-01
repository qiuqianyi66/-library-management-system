<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="读者姓名" prop="readerName">
        <el-input
          v-model="queryParams.readerName"
          placeholder="请输入读者姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="罚款状态" prop="fineStatus">
        <el-select v-model="queryParams.fineStatus" placeholder="请选择罚款状态" clearable>
          <el-option label="未缴纳" value="0" />
          <el-option label="已缴纳" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="支付方式" prop="payMethod">
        <el-select v-model="queryParams.payMethod" placeholder="请选择支付方式" clearable>
          <el-option
            v-for="dict in dict.type.pay_method"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="fineList">
      <el-table-column label="罚款ID" align="center" prop="fineId" />
      <el-table-column label="借阅ID" align="center" prop="borrowId" />
      <el-table-column label="读者姓名" align="center" prop="readerName" />
      <el-table-column label="罚款金额" align="center" prop="amount">
        <template slot-scope="scope">
          <span style="color: #ff0000;">{{ scope.row.amount }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '0' ? 'danger' : 'success'">{{ scope.row.status === '0' ? '未缴纳' : '已缴纳' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="支付方式" align="center" prop="payMethod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pay_method" :value="scope.row.payMethod"/>
        </template>
      </el-table-column>
      <el-table-column label="支付时间" align="center" prop="payTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.payTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-money"
            @click="handlePay(scope.row)"
            v-if="scope.row.status === '0'"
            v-hasPermi="['bookmanager:fine:pay']"
          >支付</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listFine, getFine, payFine } from "@/api/bookmanager/fine";

export default {
  name: "Fine",
  dicts: ['pay_method'],
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      fineList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        readerName: null,
        fineStatus: null,
        payMethod: null
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询罚款管理列表 */
    getList() {
      this.loading = true;
      listFine(this.queryParams).then(response => {
        this.fineList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 支付按钮操作 */
    handlePay(row) {
      this.$modal.confirm('确认支付 ¥' + row.amount + ' 罚款？').then(() => {
        payFine({ fineId: row.fineId }).then(response => {
          this.$modal.msgSuccess("支付成功");
          this.getList();
        });
      }).catch(() => {
      });
    }
  }
};
</script>
