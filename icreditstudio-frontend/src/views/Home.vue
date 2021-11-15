<template>
  <div class="home iframe-flex-row-center-center h100">
    <div class="home-left">
      <div class="home-left-gif"></div>
      <div class="home-left-func">
        <div class="title">
          <img src="../assets/home.gif" />
        </div>
        <div class="func-wrap">
          <div class="func-item">
            <j-svg name="menu-space" />
            <span>工作空间</span>
          </div>
        </div>
      </div>
    </div>
    <div class="home-right"></div>
  </div>
</template>

<script>
export default {
  name: 'Home'
}
</script>
<style scoped>
.home {
  width: 100%;
  height: 100%;
  background-color: #fff;
  font-size: 10em;
  font-weight: 700;
}
</style>
