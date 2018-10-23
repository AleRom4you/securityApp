(function() {
  // Left Drawer
  const leftDrawerEL = document.querySelector('#left-drawer');
  // Toolbar burger icon
  const burgerIcon = document.querySelector('#portal-top-toolbar-burger-icon');
  // Sidemenu close icon
  const closeIcon = document.querySelector('#portal-sidemenu-close-icon');
  // Left drawer
  const leftDrawer = new mdc.drawer.MDCTemporaryDrawer(leftDrawerEL);

  // Setting up menu items on toolbar menu
  const toolbarDashboardMenuEl = document.querySelector('#portal-toolbar-menu-dashboard');
  if (toolbarDashboardMenuEl !== null) {
    const toolbarDashboardMenu = new mdc.menu.MDCMenu(toolbarDashboardMenuEl);
    document.querySelector('#portal-toolbar-menu-dashboard-button').addEventListener('click', function() {
      toolbarDashboardMenu.open = !toolbarDashboardMenu.open;
    });
    // Set Anchor Corner to Bottom End
    toolbarDashboardMenu.setAnchorCorner(mdc.menu.MDCMenuFoundation.Corner.BOTTOM_START);
  }
  const toolbarAppsMenuEl = document.querySelector('#portal-toolbar-menu-apps');
  if (toolbarAppsMenuEl !== null) {
    const toolbarAppsMenu = new mdc.menu.MDCMenu(toolbarAppsMenuEl);
    document.querySelector('#portal-toolbar-menu-apps-button').addEventListener('click', function() {
      toolbarAppsMenu.open = !toolbarAppsMenu.open;
    });
    // Set Anchor Corner to Bottom End
    toolbarAppsMenu.setAnchorCorner(mdc.menu.MDCMenuFoundation.Corner.BOTTOM_START);
  }
  const toolbarFormControlsMenuEl = document.querySelector('#portal-toolbar-menu-form-controls');
  if (toolbarFormControlsMenuEl !== null) {
    const toolbarFormControlsMenu = new mdc.menu.MDCMenu(toolbarFormControlsMenuEl);
    document.querySelector('#portal-toolbar-menu-form-controls-button').addEventListener('click', function() {
      toolbarFormControlsMenu.open = !toolbarFormControlsMenu.open;
    });
    // Set Anchor Corner to Bottom End
    toolbarFormControlsMenu.setAnchorCorner(mdc.menu.MDCMenuFoundation.Corner.BOTTOM_START);
  }
  const toolbarLayoutMenuEl = document.querySelector('#portal-toolbar-menu-layout');
  if (toolbarLayoutMenuEl !== null) {
    const toolbarLayoutMenu = new mdc.menu.MDCMenu(toolbarLayoutMenuEl);
    document.querySelector('#portal-toolbar-menu-layout-button').addEventListener('click', function() {
      toolbarLayoutMenu.open = !toolbarLayoutMenu.open;
    });
    // Set Anchor Corner to Bottom End
    toolbarLayoutMenu.setAnchorCorner(mdc.menu.MDCMenuFoundation.Corner.BOTTOM_START);
  }
  const toolbarPopupsMenuEl = document.querySelector('#portal-toolbar-menu-popups');
  if (toolbarPopupsMenuEl !== null) {
    const toolbarPopupsMenu = new mdc.menu.MDCMenu(toolbarPopupsMenuEl);
    document.querySelector('#portal-toolbar-menu-popups-button').addEventListener('click', function() {
      toolbarPopupsMenu.open = !toolbarPopupsMenu.open;
    });
    // Set Anchor Corner to Bottom End
    toolbarPopupsMenu.setAnchorCorner(mdc.menu.MDCMenuFoundation.Corner.BOTTOM_START);
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

  const tfRoot = document.querySelectorAll('.portal-navigation-list__item');
  for (let i = 0; i < tfRoot.length; i++) {
    tfRoot[i].addEventListener('click', function(e) {
      if (/#/.test(this.href)) {
        e.preventDefault();
        this.classList.toggle('portal-navigation-list__item--open');
      }
    });
  }
})();
