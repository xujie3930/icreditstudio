<template>
  <span
    class="content-tag"
    :class="`content-tag--${effect} content-tag--${type}`"
  >
    {{ label }}
    <slot />
  </span>
</template>

<script>
export default {
  name: 'Tag',
  props: {
    type: {
      type: String,
      default: ''
    },
    effect: {
      type: String,
      default: 'plain'
    },
    label: {
      type: String,
      default: ''
    }
  },
  computed: {

  },
  methods: {

  }
}
</script>

<style scoped>
.content-tag{
    height: 24px;
    padding: 0 8px;
    line-height: 22px;
    background-color: #fef2e8;
    border-color: #fce5d2;
    display: inline-block;
    font-size: 12px;
    color: #F17D1D;
    border-width: 1px;
    border-style: solid;
    border-radius: 4px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    white-space: nowrap;
}
.content-tag--plain {
    background-color: #fff;
    border-color: #3794FF;
    color: #3794FF;
}
.content-tag--dark {
    background-color: #3794FF;
    border-color: #3794FF;
    color: #fff;
}
.content-tag--plain.content-tag--success {
    background-color: #fff;
    border-color: #9ae999;
    color: #02c800;
}
.content-tag--dark.content-tag--success {
    background-color: #02c800;
    border-color: #02c800;
    color: #fff;
}
.content-tag--plain.content-tag--info {
    background-color: #fff;
    border-color: #e6e7e8;
    color: #c0c2c6;
}
.content-tag--dark.content-tag--info {
    background-color: #c0c2c6;
    border-color: #c0c2c6;
    color: #fff;
}
.content-tag--plain.content-tag--danger {
    background-color: #fff;
    border-color: #eda0a3;
    color: #d1121a;
}
.content-tag--dark.content-tag--danger {
    background-color: #d1121a;
    border-color: #d1121a;
    color: #fff;
}
.content-tag--plain.content-tag--warning {
    background-color: #fff;
    border-color: #F17D1D;
    color: #F17D1D;
}
.content-tag--dark.content-tag--warning {
    background-color: #F17D1D;
    border-color: #F17D1D;
    color: #fff;
}
.content-tag--plain.content-tag--TOAUDIT {
    background-color: rgba(55,148,255,0.1);
    border-color: #3794FF;
    color: #3794FF;
}
.content-tag--plain.content-tag--RECALL {
    background-color: rgba(102,102,102,0.1);
    border-color: #999999;
    color: #999999;
}
.content-tag--plain.content-tag--GOBACK {
    background-color: rgba(246,184,53,0.1);
    border-color: #F6B835;
    color: #F6B835;
}
.content-tag--plain.content-tag--PASS {
    background-color: rgba(2,200,0,0.1);
    border-color: #02C800;
    color: #02C800;
}
.content-tag--plain.content-tag--NOPASS {
    background-color: rgba(209,18,26,0.1);
    border-color: #D1121A;
    color: #D1121A;
}
</style>
