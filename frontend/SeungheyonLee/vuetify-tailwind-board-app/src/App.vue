<template>
  <v-app>
    <v-main class="pa-4">
      <div class="text-3xl text-center text-blue-500 mb-6">
        Vuetify 3 + Tailwind + Module Federation
      </div>

      <v-card class="mx-auto" max-width="400">
        <v-card-title>Vuetify Card</v-card-title>
        <v-card-text>This is a Vuetify card rendered inside App.vue</v-card-text>
        <v-card-actions>
          <v-btn color="primary" @click="clicked = !clicked">
            Click Me
          </v-btn>
        </v-card-actions>
      </v-card>

      <div v-if="clicked" class="mt-4 text-green-500 text-center">
        Button was clicked!
      </div>
    </v-main>
  </v-app>
</template>

<script lang="ts" setup>
import { ref } from 'vue'

const clicked = ref(false)
</script>
