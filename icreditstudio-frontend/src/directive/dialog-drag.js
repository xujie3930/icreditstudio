import Vue from 'vue';
// 自定义弹窗拖拽
Vue.directive('dialog-drag', {
  bind(el) {
    // 声明可以移动的区域
    const headerDrag = el.querySelector('.el-dialog__header');
    // 声明移动的主体
    const mainDrag = el.querySelector('.el-dialog');

    // 修改移动区域的鼠标样式
    headerDrag.style.cursor = 'move';

    // 获取原有属性 ie - currentStyle  火狐谷歌 - window.getComputedStyle
    const style = mainDrag.currentStyle || window.getComputedStyle(mainDrag, null);

    // 鼠标按下事件
    headerDrag.onmousedown = e => {
      // 计算当前元素距离可视区的距离
      const x = e.clientX - headerDrag.offsetLeft;
      const y = e.clientY - headerDrag.offsetTop;

      // 获取到的值由于带有px，因此用正则替换
      let _left;
      let _top;
      if (style.left.includes('%')) {
        // eslint-disable-next-line no-useless-escape
        _left = +document.body.clientWidth * (+style.left.replace(/\%/g, '') / 100);
        // eslint-disable-next-line no-useless-escape
        _top = +document.body.clientHeight * (+style.top.replace(/\%/g, '') / 100);
      } else {
        _left = +style.left.replace(/\px/g, '');
        _top = +style.top.replace(/\px/g, '');
      }

      document.onmousemove = event => {
        // 鼠标按下移动时，计算移动的距离
        const left = event.clientX - x;
        const top = event.clientY - y;

        // 将获取到的移动的值赋值给移动的元素，完成元素的移动拖拽
        mainDrag.style.left = `${left + _left}px`;
        mainDrag.style.top = `${top + _top}px`;
      };

      document.onmouseup = () => {
        // 鼠标放开时,解绑移除鼠标移动事件以及鼠标按下事件
        document.onmousemove = null;
        document.onmouseup = null;
      };
    };
  }
});
