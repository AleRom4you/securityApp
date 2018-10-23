(function() {
  let menuElements = document.querySelectorAll('.mdc-menu-anchor');

  menuElements.forEach((el, index) => {
    let menuEl = el.querySelector('.portal-dropdown-menu');
    if (menuEl !== null) {
      let menu = new mdc.menu.MDCMenu(menuEl);
      let menuButtonEl = el.querySelector('.portal-menu-button');
      menuButtonEl.addEventListener('click', function() {
        menu.open = !menu.open;
      });
      // Set Anchor Corner to Bottom End
      menu.setAnchorCorner(mdc.menu.MDCMenuFoundation.Corner.BOTTOM_START);
    }
  });
})();

(function() {
  // Left Drawer
  const leftDrawerEL = document.querySelector('#left-drawer');
  const dashboardDrawerSecondLevelEL = document.querySelector('#dashboard-drawer-second-level');
  const appDrawerSecondLevelEL = document.querySelector('#app-drawer-second-level');
  const elementsDrawerSecondLevelEL = document.querySelector('#elements-drawer-second-level');
  const mq = window.matchMedia('(min-width: 960px)');
  // Toolbar burger icon
  const burgerIcon = document.querySelector('#portal-top-toolbar-burger-icon');
  // Sidemenu close icon
  const closeIcon = document.querySelector('#portal-sidemenu-close-icon');

  // Set Dashboard menu item element
  const dashboardMenuLink = document.querySelector('#dashboard-submenu');
  // Set App menu item element
  const appMenuLink = document.querySelector('#apps-submenu');
  // Set Elements menu item element
  const elementsMenuLink = document.querySelector('#elements-submenu');

  // We change drawer type depending on device.
  let leftDrawer;
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
  if (dashboardDrawerSecondLevelEL !== null) {
    dashboardDrawerSecondLevel = new mdc.drawer.MDCTemporaryDrawer(dashboardDrawerSecondLevelEL);
  }
  if (appDrawerSecondLevelEL !== null) {
    appDrawerSecondLevel = new mdc.drawer.MDCTemporaryDrawer(appDrawerSecondLevelEL);
  }
  if (elementsDrawerSecondLevelEL !== null) {
    elementsDrawerSecondLevel = new mdc.drawer.MDCTemporaryDrawer(elementsDrawerSecondLevelEL);
  }

  // Toggle menu when burger menu is clicked
  burgerIcon.addEventListener('click', function(e) {
    leftDrawer.open = !leftDrawer.open;
    e.preventDefault();
  });

  // Toggle menu when item with children is clicked
  dashboardMenuLink.addEventListener('click', function(e) {
    elementsDrawerSecondLevel.open = false;
    appDrawerSecondLevel.open = false;
    dashboardDrawerSecondLevel.open = !dashboardDrawerSecondLevel.open;
    e.preventDefault();
  });
  appMenuLink.addEventListener('click', function(e) {
    elementsDrawerSecondLevel.open = false;
    dashboardDrawerSecondLevel.open = false;
    appDrawerSecondLevel.open = !appDrawerSecondLevel.open;
    e.preventDefault();
  });
  elementsMenuLink.addEventListener('click', function(e) {
    appDrawerSecondLevel.open = false;
    dashboardDrawerSecondLevel.open = false;
    elementsDrawerSecondLevel.open = !elementsDrawerSecondLevel.open;
    e.preventDefault();
  });

  // Toggle menu when close sidemenu icon is clicked
  closeIcon.addEventListener('click', function(e) {
    leftDrawer.open = false;
    dashboardDrawerSecondLevel.open = false;
    appDrawerSecondLevel.open = false;
    elementsDrawerSecondLevel.open = false;
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
