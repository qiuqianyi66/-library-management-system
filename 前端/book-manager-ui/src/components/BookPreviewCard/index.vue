<template>
  <transition name="preview-fade">
    <div v-if="visible" class="book-preview-glass" :style="position">
      <div class="preview-arrow"></div>
      <div class="preview-cover">
        <img v-if="book.coverUrl" :src="book.coverUrl" />
        <span v-else class="preview-cover-placeholder">{{ (book.bookName && book.bookName[0]) || '📖' }}</span>
      </div>
      <div class="preview-info">
        <div class="preview-title">{{ book.bookName }}</div>
        <div class="preview-meta">{{ book.author }} · {{ book.press }}</div>
        <div class="preview-rating" v-if="book.avgRating">
          <book-rating :value="book.avgRating" :readonly="true" />
          <span class="preview-rating-text">{{ book.avgRating }} ({{ book.ratingCount || 0 }}人)</span>
        </div>
        <div class="preview-desc" v-if="book.description">{{ truncate(book.description, 60) }}</div>
        <div class="preview-footer">
          <el-tag :type="book.stockCount > 0 ? 'success' : 'info'" size="mini">
            {{ book.stockCount > 0 ? '可借 ' + book.stockCount + ' 册' : '已借完' }}
          </el-tag>
          <span class="preview-isbn" v-if="book.isbn">ISBN: {{ book.isbn }}</span>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import BookRating from "@/components/BookRating"

export default {
  name: 'BookPreviewCard',
  components: { BookRating },
  props: {
    book: { type: Object, default: () => ({}) },
    visible: { type: Boolean, default: false },
    position: { type: Object, default: () => ({ top: '0', left: '0' }) }
  },
  methods: {
    truncate(text, len) {
      if (!text) return ''
      return text.length > len ? text.slice(0, len) + '…' : text
    }
  }
}
</script>

<style scoped>
.book-preview-glass {
  position: fixed;
  z-index: 9999;
  display: flex;
  gap: 14px;
  padding: 14px;
  width: 340px;
  background: var(--vibe-bg-elevated, rgba(255,255,255,0.95));
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  border: 1px solid var(--vibe-border-light, rgba(255,255,255,0.3));
  border-radius: 14px;
  box-shadow: 0 12px 40px rgba(0,0,0,0.15);
  pointer-events: none;
}
.preview-arrow {
  position: absolute;
  top: -6px;
  left: 30px;
  width: 12px;
  height: 12px;
  background: var(--vibe-bg-elevated, #fff);
  border-left: 1px solid var(--vibe-border-light);
  border-top: 1px solid var(--vibe-border-light);
  transform: rotate(45deg);
}
.preview-cover {
  width: 70px;
  height: 100px;
  border-radius: 6px;
  overflow: hidden;
  flex-shrink: 0;
  background: var(--vibe-bg-hover);
  display: flex;
  align-items: center;
  justify-content: center;
}
.preview-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.preview-cover-placeholder {
  font-size: 28px;
  opacity: 0.4;
}
.preview-info {
  flex: 1;
  min-width: 0;
}
.preview-title {
  font-weight: 600;
  font-size: 14px;
  color: var(--vibe-text);
  line-height: 1.4;
  margin-bottom: 3px;
}
.preview-meta {
  font-size: 12px;
  color: var(--vibe-text-muted);
  margin-bottom: 6px;
}
.preview-rating {
  display: flex;
  align-items: center;
  gap: 4px;
  margin-bottom: 6px;
}
.preview-rating-text {
  font-size: 11px;
  color: var(--vibe-text-muted);
}
.preview-desc {
  font-size: 12px;
  color: var(--vibe-text-secondary);
  line-height: 1.5;
  margin-bottom: 6px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
.preview-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.preview-isbn {
  font-size: 10px;
  color: var(--vibe-text-placeholder);
}

/* 动画 */
.preview-fade-enter-active {
  animation: previewIn 0.2s ease;
}
.preview-fade-leave-active {
  animation: previewOut 0.15s ease;
}
@keyframes previewIn {
  from { opacity: 0; transform: translateY(6px); }
  to { opacity: 1; transform: translateY(0); }
}
@keyframes previewOut {
  from { opacity: 1; transform: translateY(0); }
  to { opacity: 0; transform: translateY(4px); }
}
</style>
