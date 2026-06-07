<template>
  <div class="book-rating">
    <span
      v-for="star in maxStars"
      :key="star"
      class="star"
      :class="{ filled: star <= currentValue, clickable: readonly === false }"
      @click="setRating(star)"
    >
      ★
    </span>
    <span v-if="showText" class="rating-text">
      {{ currentValue }} / {{ maxStars }}
    </span>
  </div>
</template>

<script>
export default {
  name: 'BookRating',
  props: {
    value: {
      type: Number,
      default: 0
    },
    maxStars: {
      type: Number,
      default: 5
    },
    readonly: {
      type: Boolean,
      default: false
    },
    showText: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      currentValue: this.value
    }
  },
  watch: {
    value(val) {
      this.currentValue = val
    }
  },
  methods: {
    setRating(star) {
      if (this.readonly) return
      this.currentValue = star
      this.$emit('input', star)
      this.$emit('change', star)
    }
  }
}
</script>

<style scoped>
.book-rating {
  display: inline-flex;
  align-items: center;
}
.star {
  font-size: 20px;
  color: #d9d9d9;
  transition: color 0.2s;
  margin-right: 2px;
}
.star.filled {
  color: #f5a623;
}
.star.clickable {
  cursor: pointer;
}
.star.clickable:hover {
  color: #f5a623;
}
.star.clickable:hover ~ .star {
  color: #d9d9d9;
}
.rating-text {
  font-size: 14px;
  color: #909399;
  margin-left: 8px;
}
</style>
