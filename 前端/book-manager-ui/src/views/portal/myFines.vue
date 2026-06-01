<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="fineList">
      <el-table-column label="罚款金额" align="center" prop="fineAmount">
        <template slot-scope="scope">
          <span style="color: #f56c6c;">¥{{ scope.row.fineAmount }}</span>
        </template>
      </el-table-column>
      <el-table-column label="借阅图书名称" align="center" prop="bookName" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="payStatus">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.payStatus === '0'" type="danger">未缴纳</el-tag>
          <el-tag v-else-if="scope.row.payStatus === '1'" type="success">已缴纳</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="支付方式" align="center" prop="payMethod" />
      <el-table-column label="支付时间" align="center" prop="payTime" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.payTime ? parseTime(scope.row.payTime, '{y}-{m}-{d} {h}:{i}:{s}') : '-' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-money"
            @click="handlePay(scope.row)"
            v-if="scope.row.payStatus === '0'"
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
import { myFines, portalPay } from "@/api/portal/index";

export default {
  name: "MyFines",
  data() {
    return {
      loading: false,
      total: 0,
      fineList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      myFines(this.queryParams).then(response => {
        this.fineList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handlePay(row) {
      this.$modal.confirm('确认支付罚款 ¥' + row.fineAmount + ' 吗？').then(function () {
        return portalPay({ fineId: row.fineId });
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("支付成功");
      }).catch(() => {
      });
    }
  }
};
</script>
