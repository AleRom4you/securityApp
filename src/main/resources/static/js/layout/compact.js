(function() {
  const menuElements = document.querySelectorAll('.mdc-menu-anchor');

  menuElements.forEach((el, index) => {
    const menuEl = el.querySelector('.portal-dropdown-menu');
    if (menuEl !== null) {
      const menu = new mdc.menu.MDCMenu(menuEl);

      const menuButtonEl = el.querySelector('.portal-menu-button');
      menuButtonEl.addEventListener('click', function(e) {
        menu.open = !menu.open;
        e.preventDefault();
      });
      // Set Anchor Corner to Bottom End
      menu.setAnchorCorner(mdc.menu.MDCMenuFoundation.Corner.BOTTOM_START);
    }
  });

  // Left Drawer
  const leftDrawerEL = document.querySelector('#left-drawer');
  const mq = window.matchMedia('(min-width: 960px)');
  // Toolbar burger icon
  const burgerIcon = document.querySelector('#portal-top-toolbar-burger-icon');
  // Sidemenu close icon
  const closeIcon = document.querySelector('#portal-sidemenu-close-icon');

  let leftDrawer;
  // We change drawer type depending on device.
  if (mq.matches) {
    // Persistant drawer on desktop.
    leftDrawer = new mdc.drawer.MDCPersistentDrawer(leftDrawerEL);
  } else {
    // Temporary drawer on mobile.
    leftDrawerEL.classList.remove('mdc-drawer--persistent', 'mdc-drawer--open');
    leftDrawerEL.classList.add('mdc-drawer--temporary', 'mdc-drawer--closed');
    leftDrawer = new mdc.drawer.MDCTemporaryDrawer(leftDrawerEL);
    // Show menu
    burgerIcon.style.display = 'block';
  }

  // Toggle menu when burger menu is clicked
  burgerIcon.addEventListener('click', function(e) {
    leftDrawer.open = !leftDrawer.open;
    e.preventDefault();
  });

  // Toggle menu when close sidemenu icon is clicked
  closeIcon.addEventListener('click', function(e) {
    leftDrawer.open = !leftDrawer.open;
    e.preventDefault();
  });

  // Watch for changes to the drawer and show and hide burger icon.
  leftDrawerEL.addEventListener('MDCPersistentDrawer:open', function() {
    burgerIcon.style.display = 'none'
  });
  leftDrawerEL.addEventListener('MDCPersistentDrawer:close', function() {
    burgerIcon.style.display = 'block'
  });
})();
