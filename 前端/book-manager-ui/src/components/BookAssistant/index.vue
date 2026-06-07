<template>
  <div v-if="visible" class="book-assistant" :class="[mood]" @click="handleClick">
    <!-- 小书灵 SVG -->
    <div class="assistant-body">
      <svg viewBox="0 0 120 140" width="56" height="65" class="assistant-svg">
        <!-- 身体 -->
        <ellipse cx="60" cy="95" rx="35" ry="30" fill="var(--vibe-primary)" opacity="0.15" />
        <!-- 书本底座 -->
        <rect x="40" y="108" width="40" height="8" rx="4" fill="var(--vibe-primary)" opacity="0.3" />
        <!-- 书本 -->
        <g :class="{ 'book-bounce': mood === 'happy' }">
          <rect x="44" y="70" width="32" height="26" rx="3" fill="var(--vibe-primary)" opacity="0.85" />
          <line x1="60" y1="70" x2="60" y2="96" stroke="rgba(255,255,255,0.3)" stroke-width="1" />
          <text x="60" y="88" text-anchor="middle" font-size="10" fill="white">📖</text>
        </g>
        <!-- 眼睛 -->
        <g class="eyes">
          <ellipse cx="50" cy="58" rx="5" ry="6" fill="var(--vibe-text)" :class="{ 'eye-closed': mood === 'sleepy' }" />
          <ellipse cx="70" cy="58" rx="5" ry="6" fill="var(--vibe-text)" :class="{ 'eye-closed': mood === 'sleepy' }" />
          <!-- 瞳孔 -->
          <circle v-if="mood !== 'sleepy'" cx="50" cy="58" r="2.5" fill="white" />
          <circle v-if="mood !== 'sleepy'" cx="70" cy="58" r="2.5" fill="white" />
          <!-- 睡眼 -->
          <line v-if="mood === 'sleepy'" x1="46" y1="58" x2="54" y2="58" stroke="var(--vibe-text)" stroke-width="1.5" stroke-linecap="round" />
          <line v-if="mood === 'sleepy'" x1="66" y1="58" x2="74" y2="58" stroke="var(--vibe-text)" stroke-width="1.5" stroke-linecap="round" />
        </g>
        <!-- 嘴巴 -->
        <ellipse v-if="mood === 'happy'" cx="60" cy="67" rx="4" ry="2.5" fill="var(--vibe-primary)" opacity="0.6" />
        <circle v-else cx="60" cy="67" r="1.5" fill="var(--vibe-text)" opacity="0.4" />
        <!-- 翅膀 (Zzz 睡觉) -->
        <text v-if="mood === 'sleepy'" x="80" y="50" font-size="8" fill="var(--vibe-text-muted)" class="zzz">Z</text>
        <text v-if="mood === 'sleepy'" x="86" y="44" font-size="6" fill="var(--vibe-text-muted)" class="zzz zzz2">Z</text>
      </svg>
    </div>

    <!-- 气泡消息 -->
    <transition name="msg-pop">
      <div v-if="message" class="assistant-message" @click.stop>
        <div class="msg-text">{{ message }}</div>
        <div class="msg-tail"></div>
      </div>
    </transition>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'BookAssistant',
  data() {
    return {
      mood: 'happy',    // happy | sleepy | thinking
      message: '',
      visible: true,
      messageTimer: null,
      moodTimer: null,
      greetings: [
        '今天想读什么书呢？📚',
        '我来帮你找本书吧！🦉',
        '书架上有新书到了哦~',
        '阅读让生活更美好 ✨',
        '要不要试试「深夜阅读角」主题？🌙'
      ],
      reminders: [
        '别忘了还书日期哦~',
        '逾期会生成罚款，记得及时归还！',
        '你还有书快到期了，可以续借~'
      ]
    }
  },
  computed: {
    ...mapState('ambiance', {
      assistantEnabled: state => state.assistantEnabled
    })
  },
  watch: {
    assistantEnabled(val) {
      this.visible = val
      if (val) this.greet()
      else this.message = ''
    }
  },
  mounted() {
    this.visible = this.assistantEnabled
    if (this.visible) {
      setTimeout(() => this.greet(), 2000)
      this.startMoodCycle()
    }
  },
  beforeDestroy() {
    clearTimeout(this.messageTimer)
    clearInterval(this.moodTimer)
  },
  methods: {
    greet() {
      const msg = this.greetings[Math.floor(Math.random() * this.greetings.length)]
      this.showMessage(msg, 4000)
    },
    showMessage(text, duration = 3000) {
      this.message = text
      clearTimeout(this.messageTimer)
      this.messageTimer = setTimeout(() => {
        this.message = ''
      }, duration)
    },
    handleClick() {
      // 点击切换心情
      const msgs = [...this.greetings, '戳我干嘛~ 🦉', '好好看书！📖']
      const msg = msgs[Math.floor(Math.random() * msgs.length)]
      this.mood = 'happy'
      this.showMessage(msg, 3000)
    },
    startMoodCycle() {
      this.moodTimer = setInterval(() => {
        const moods = ['happy', 'sleepy', 'thinking', 'happy', 'happy']
        this.mood = moods[Math.floor(Math.random() * moods.length)]
      }, 8000)
    },
    // 对外暴露：显示温和提醒
    gentleRemind(text) {
      this.mood = 'thinking'
      this.showMessage(text || this.reminders[Math.floor(Math.random() * this.reminders.length)], 5000)
    }
  }
}
</script>

<style scoped>
.book-assistant {
  position: fixed;
  right: 20px;
  bottom: 155px;
  z-index: 1999;
  cursor: pointer;
  transition: transform 0.3s ease, opacity 0.5s ease;
}
.book-assistant:hover {
  transform: scale(1.08);
}
.book-assistant.happy .assistant-svg {
  animation: float 3s ease-in-out infinite;
}

/* 气泡消息 */
.assistant-message {
  position: absolute;
  right: 60px;
  bottom: 10px;
  width: 200px;
  padding: 10px 14px;
  background: var(--vibe-bg-elevated, #fff);
  border-radius: 12px;
  box-shadow: var(--vibe-shadow, 0 4px 16px rgba(0,0,0,0.12));
  border: 1px solid var(--vibe-border-light, #ebeef5);
  pointer-events: auto;
}
.msg-text {
  font-size: 13px;
  color: var(--vibe-text, #303133);
  line-height: 1.5;
}
.msg-tail {
  position: absolute;
  right: -6px;
  bottom: 14px;
  width: 10px;
  height: 10px;
  background: var(--vibe-bg-elevated, #fff);
  border-right: 1px solid var(--vibe-border-light, #ebeef5);
  border-bottom: 1px solid var(--vibe-border-light, #ebeef5);
  transform: rotate(-45deg);
}

/* 消息弹出动画 */
.msg-pop-enter-active { animation: popIn 0.3s ease; }
.msg-pop-leave-active { animation: popOut 0.2s ease; }
@keyframes popIn {
  from { opacity: 0; transform: translateX(10px) scale(0.9); }
  to { opacity: 1; transform: translateX(0) scale(1); }
}
@keyframes popOut {
  from { opacity: 1; transform: translateX(0) scale(1); }
  to { opacity: 0; transform: translateX(10px) scale(0.9); }
}

/* 漂浮动画 */
@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-6px); }
}

/* 眨眼 */
.eye-closed {
  opacity: 0.3;
}

/* Zzz */
.zzz {
  animation: zzzAnim 2s ease-in-out infinite;
}
.zzz2 {
  animation-delay: 0.5s;
}
@keyframes zzzAnim {
  0%, 100% { opacity: 0; transform: translateY(0); }
  50% { opacity: 0.8; transform: translateY(-4px); }
}
</style>
