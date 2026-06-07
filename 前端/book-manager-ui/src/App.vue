<template>
  <div id="app">
    <router-view />
    <theme-picker />
    <ambiance-picker />
    <book-assistant />
    <ambient-sound />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker";
import AmbiancePicker from "@/components/AmbiancePicker";
import BookAssistant from "@/components/BookAssistant";
import AmbientSound from "@/components/AmbientSound";

export default {
  name: "App",
  components: { ThemePicker, AmbiancePicker, BookAssistant, AmbientSound },
  created() {
    this.$store.dispatch('ambiance/initTheme')
  },
  metaInfo() {
    return {
      title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  }
};
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
