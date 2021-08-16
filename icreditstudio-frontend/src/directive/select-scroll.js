import Vue from 'vue';

// 自定义选择框下拉滚动加载事件
Vue.directive('select-scroll', {
  bind(el, binding) {
    // 获取element-ui定义好的scroll盒子
    const _selectDom = el.querySelector('.el-select-dropdown .el-select-dropdown__wrap');
    _selectDom.addEventListener('scroll', function () {
      /**
       * scrollHeight 获取元素内容高度(只读)
       * scrollTop 获取或者设置元素的偏移值,常用于, 计算滚动条的位置, 当一个元素的容器没有产生垂直方向的滚动条, 那它的scrollTop的值默认为0.
       * clientHeight 读取元素的可见高度(只读)
       * 如果元素滚动到底, 下面等式返回true, 没有则返回false:
       * ele.scrollHeight - ele.scrollTop === ele.clientHeight;
       */
      const _isScroll = this.scrollHeight - this.scrollTop <= this.clientHeight;
      if (_isScroll) {
        binding.value();
      }
    });
  }
});
