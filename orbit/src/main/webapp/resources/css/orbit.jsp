<%@page contentType="text/css" %>

body {
  background-color: #FFFFFF;
  top:0px;
  left:0px;
  right:0px;
  bottom:0px;
  padding:0px;
  margin:0px;
  overflow: hidden;
  font-family: Droid;
  color: white;
  text-shadow: 0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px black;
}

#logger {
  position: fixed;
  bottom: 0px;
  left: 0px;
  right: 0px;
  height: 30px;
  text-align: center;
  opacity: 0;
  background: linear-gradient(transparent, white);
  pointer-events: none;
  transition: opacity 2s;
  z-index: 1000;
  color: white;
  text-shadow: 0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px black;
}

#logger.toggle {
  opacity: 1;
}



pre.error {
  background-color: rgba(0,0,0,0.9);
  border:2px solid  rgba(128,128,128,.95);
  color: #CC0000;
  font-size:12px;
  padding: 7px;
  box-shadow: 5px 5px 5px 0px #000;
  white-space: pre-wrap;       /* css-3 */
  white-space: -moz-pre-wrap;  /* Mozilla, since 1999 */
  white-space: -pre-wrap;      /* Opera 4-6 */
  white-space: -o-pre-wrap;    /* Opera 7 */
  word-wrap: break-word; 
}

@font-face {
  font-family: Journey;
  src:	url('fonts/JourneyPS3.ttf');
  font-weight: 400;
}

.journeyFont {
  font-family: Journey, Helvetica, Arial, sans-serif;
}

@font-face {
  font-family: Droid;
  src: url('fonts/DroidSansMono.ttf');
/*  font-weight: 400;*/
}

.droidFont {
  font-family: Droid, "Lucida Console", Monaco, monospace;
}

.orbitLogo {
  font-size: 55px;
  height: 100%;
  width: 100%;
  position: fixed;
  left: -100%;
  z-index: 51;
}

.orbitLogo.toggle {
  left:0%;
}


.orbitLogo > .orbitTxt {
  position: absolute;
  top: 50%;
  left: 30px;
  width: 95%;
  text-align:center;
}

.wiper {
  background: linear-gradient(to right, white, white, white, white, transparent);
  position: fixed;
  top: 0px;
  bottom: 0px;
  width: 300%;
  left: -300%;
  z-index: 100;
  transition: left 1s;
}

.wiper.toggle {
  left: 0%;
}

.sky {
  position: absolute;
  top: 0px;
  right: 100%;
  width: 100%;
  bottom: 0px;
  z-index: 0;
  overflow: hidden;
}

.sky .rain {
  position: fixed;
  top: 0px;
  right: 0px;
  bottom: 0px;
  left: 0px;
  z-index: 20;
  opacity: 0;
  transition: opacity 1s;
/*  background-color: black;*/
  background: #0D343A;
  background: -webkit-gradient(linear,0% 0%,0% 100%, from(rgba(13,52,58,1) ), to(#000000) );
  background: -moz-linear-gradient(top, rgba(13,52,58,1) 0%, rgba(0,0,0,1) 100%);
  overflow: hidden;
}

.sky .rain .flash {
  position: absolute;
  top: 0px;
  right: 0px;
  bottom: 0px;
  left: 0px;
  z-index: 15;
  background: linear-gradient(rgba(255,255,255,0.8),rgba(255,255,255,0.4));
  opacity: 0;
  display: none;
}

.sky.stormy .rain .flash {
  display: block;
}


.sky .rain .flash.lit1 {
  animation: lightningFlash1 1s;
}

.sky .rain .flash.lit2 {
  animation: lightningFlash1 1s;
}

.sky .rain .flash.lit3 {
  animation: lightningFlash1 1s;
}


.sky .rain .rainCloud {
  position: absolute;
  top: 0px;
  right: 0px;
  bottom: 0px;
  left: 0px;
  z-index: 50;
  background-image: linear-gradient(rgba(70,70,70,1), rgba(70,70,70,0.9), rgba(70,70,70,0.7), transparent, transparent, transparent, transparent, transparent, transparent, transparent, transparent, transparent);

}

.sky.raining .rain {
  opacity: 1;
}

.sky.stormy .rain {
  opacity: 1;
}

.sky.toggle {
  right: 0%;
}

.sky > .blue {
  position: absolute;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  background-image: linear-gradient(rgb(38, 154, 255) 0%, rgb(149, 229, 236) 100%);
  z-index: 5;
}

.sky > .cloud {
  position: absolute;
  top: 0%;
  left: 0px;
  right: 0px;
  height: 200%;
  background-image: linear-gradient(white 0%, transparent 50%, #FE5B35 100%);
  z-index: 10;
  transition: top 1s;
}

.sky > .night {
  position: absolute;
  top: -300%;
  left: 0px;
  right: 0px;
  height: 300%;
/*  background-image: linear-gradient(black 0%, #001848 20%, #301860 30%, #483078 40%, #604878 50%, #906090 60%, transparent
100%);*/
  background-image: linear-gradient(black 0%, #001848 40%, #301860 60%, transparent 100%);

  z-index: 15;
  transition: top 1s;
}

.sky.night > .night { 
  top: 0%;
}
.sky.night > .cloud {
  top: -100%;
}

.sky.yugure > .night { 
  top: -150%;
}
.sky.yugure > .cloud {
  top: -100%;
}

.sky.sunset > .night { 
  top: -200%;
}
.sky.sunset > .cloud {
  top: -100%;
}

.sky.sundown > .night { 
  top: -300%;
}
.sky.sundown > .cloud {
  top: -100%;
}

.controlsContainer {
  position: absolute;
  top:0px;
  right:0px;
  left:0px;
  bottom:0px;
  overflow: hidden;
}

.controlsContainer > div { 
/*
  background: rgba(0,0,0,0.3);
  transition: background 1s;
*/
}

.controlsContainer.toggle > div { 
/*  background: rgba(0,0,0,0.6);*/
}

.controlsContainer > .controls { 
  position: absolute;
  top: -100px;
  left: 0px;
  right: 0px;
  height: 60px;
  transition: top 1s;
}

.controlsContainer > .controls.toggle {
  top: 0px;
}

.controlsContainer > .controls > .logo {
  position: absolute;
  left: 10;
  top: -4px;
  bottom: 0;
  width: 68px;
  background-image: url("../images/orbit.png");
  background-size: contain;
  background-repeat: no-repeat;
}

.controlsContainer > .controls > .search {
  position: absolute;
  background-color: transparent;
  margin: auto;
  top: 10px;
  left: 70px;
  right: 10px;
  font-size: 23px;
  padding: 0px;
  height: 40px;
  box-shadow:  0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px black;
}


.controlsContainer > .controls > .search > .searchInput {
  position: absolute;
  outline: none;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  margin: 0;
  padding-top: 1px;
  padding-bottom: 1px;
  padding-left:10px;
  width: 100%;
  font-size: 18px;
  background-color: rgba(255,255,255,0.5);
  border: 1px solid white;
  color: black;
  transition: color 3s, text-shadow 3s;
}

.controlsContainer > .controls > .search > .searchInput:focus {
  color: black;
  text-shadow: 0px 0px 2px white, 0px 0px 2px white, 0px 0px 2px white, 0px 0px 2px white;
 /*  animation:searchText 3s linear infinite;*/
}

.controlsContainer > .controls > .search > .searchInput.searching {
  color: black;
  text-shadow: 0px 0px 2px white, 0px 0px 2px white, 0px 0px 2px white, 0px 0px 2px white;
  animation:searchText 3s linear infinite;
}


.controlsContainer > .controls > .menu {
  position: absolute;
  top: 10px;
  right: 10px;
  bottom: 10px;
  width: 40px;
  background-image: url(../images/appsShadow_b.png);
  background-repeat: no-repeat;
  background-size: contain;
  background-position: top center;
  opacity: 0.3;
  cursor: pointer;
  transition: opacity .5s;
  height: 55px;
  /* border-radius: 0px; */
  /* margin: 15px; */
  border: 5px solid rgba(0,0,0,0.0);
}

.controlsContainer > .controls > .menu[data-tooltip]:before {
  content: attr(data-tooltip);
  display: block;
  position:absolute;
  width:600px;
  height:40px;
  background-color: rgba(0,0,0,0.2);
  font-size: 30px;
  right: -5px;
  white-space: nowrap;
  padding: 5px;
  top: 60px;
  text-align: center;
  vertical-align: middle;
  line-height: 40px;
  color: white;
  opacity: 0;
  text-shadow: 0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px black;
  pointer-events: none;
}

.controlsContainer > .controls > .menu:hover  {
  background-color: rgba(0,0,0,0.2);
  opacity: 1;
}

.controlsContainer > .controls > .menu[data-tooltip]:hover:before {
  opacity: 1;
}









.controlsContainer > .dropdownMenu {
  position: absolute;
  top: 0px;
  right: -62px;
  opacity: 0;
  padding: 10px;
  bottom: -1px;
  transition: opacity .25s, right .25s;
  pointer-events: none;
}

.controlsContainer > .dropdownMenu.toggle {
  right: 0px;
  opacity: 1;
  pointer-events: auto;
}

.controlsContainer > .dropdownMenu > div {
  position: relative; 
  width: 40px;
  height: 40px;
  background-repeat: no-repeat;
  background-size: contain;
  background-position: center;
  opacity: 0.5;
  cursor: pointer;
  transition: opacity .5s;
  margin-top:3px;
  margin-bottom:10px;
  border-radius: 5px;
}

.controlsContainer > .dropdownMenu > div > .subtitle {
  display: none;
  /* pointer-events: none; */
  font-size: 10px;
  padding: 2px;
  position: absolute;
  bottom: 0px;
  left: 40px;
  background-color: white;
  border-radius: 10px;
  opacity: 1;
  z-index: 10;
  color: black;
}

.controlsContainer > .dropdownMenu > div > .subtitle.toggle {
  display: block;
}



.controlsContainer > .dropdownMenu > div[data-tooltip]:before {
  content: attr(data-tooltip);
  display: block;
  position:absolute;
  width:600px;
  height:40px;
  left:-640px;
  background-color: rgba(0,0,0,0.8);
  border: 1px solid white;
  font-size: 30px;
  right: 80px;
  white-space: nowrap;
  padding: 5px;
  top: -5px;
  text-align: center;
  vertical-align: middle;
  line-height: 40px;
  border: 1px solid white;
  color: white;
  opacity: 0;
  /*text-shadow: 0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px
  /black;*/
  pointer-events: none;
}

.controlsContainer > .dropdownMenu > div.targetActive {
  opacity: 1;
  box-shadow: 0px 0px 5px 4px white;
  opacity: 1;
}

.controlsContainer > .dropdownMenu > div[data-tooltip]:hover:before {
  opacity: 1;
}


.controlsContainer > .dropdownMenu > div:hover {
  opacity: 1;
}

.controlsContainer > .dropdownMenu > .designerIcon {
  background-image: url("../images/compassIcon.png");
}

.controlsContainer > .dropdownMenu > .weatherIcon {
  background-image: url("../images/cloudIcon.png");
}

.controlsContainer > .dropdownMenu > .cartIcon {
  background-image: url("../images/cart_white.png");
}

.controlsContainer > .dropdownMenu > .consoleIcon {
  background-image: url("../images/consoleicon.png");
}

.panels {
  position: fixed;
  top:60px;
  left:0px;
  right:0px;
  bottom:10px;
}

.panels > div {
  position: absolute;
  top:0px;
  left:10px;
  right:0px;
  bottom:0px;
  padding:15px;
  background-color: rgba(0,0,0,0.1);
  opacity: 0;
  pointer-events: none;
  transition: opacity 1s;
  overflow-y: scroll;
}

.panels > div.toggle {
  opacity: 1;
  pointer-events: auto;
}

.skySelector {
  appearance: none;
  /* -webkit-appearance: none; */
  padding: 5px;
  font-size: 18px;
  color: white;
  text-shadow: 0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px black;
  background-color: black;
  outline:none;
  box-shadow: 0px 0px 15px white;
}

.logHistory {
  position: fixed;
  left: 0px;
  right: 70px;
  height: 100%;
  top: 100%;
  transition: top .5s;
  z-index: 50;
  font-family: monospace;
  padding: 5px;
}

.logHistory.toggle {
  top: 60px;
}

.logHistory > .logEntry {
  text-overflow: ellipsis;
  white-space: nowrap;
  border-bottom: 1px solid rgba(0,0,0,.5);
  padding-bottom: 5px;
  cursor: pointer;
}

.logHistory > .logEntry:hover {
  background-color: rgba(0,0,0,0.2);  
}

.logHistory > .logEntry.toggle {
  text-overflow: clip;
  white-space: normal;  
  background-color: rgba(0,0,0,0.5);
  color: white;
}

.logHistory > .logEntry.toggle:hover {
  background-color: rgba(0,0,0,0.7);
}





















@keyframes searchText {
  0% {
    color: black;
    text-shadow: 0px 0px 2px white, 0px 0px 2px white, 0px 0px 2px white, 0px 0px 2px white;    
  } 50% {
    color: white;
    text-shadow: 0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px black, 0px 0px 2px black;    
  } 100% {
    color: black;
    text-shadow: 0px 0px 2px white, 0px 0px 2px white, 0px 0px 2px white, 0px 0px 2px white;    
  }
}

@keyframes lightningFlash1 {
  0% { opacity: 0; } 
  95% { opacity: 0; } 
  97% { opacity: .7; } 
  100% { opacity: 0; }
}

@keyframes lightningFlash2 {
  0% {
    opacity: 0;
  } 90% {
    opacity: 0;
  } 97% {
    opacity: .7;
  } 100% {
    opacity: 0;
  }
}

@keyframes lightningFlash2 {
  0% {
    opacity: 0;
  } 85% {
    opacity: 0;
  } 90% {
    opacity: .7;
  } 93% {
    opacity: 0;
  } 97% {
    opacity: .7;
  } 100% {
    opacity: 0;
  }
}

.drop {
  background:-webkit-gradient(linear,0% 0%,0% 100%, from(rgba(13,52,58,1) ), to(rgba(255,255,255,0.6))  );
  background: -moz-linear-gradient(top, rgba(13,52,58,1) 0%, rgba(255,255,255,.6) 100%);
        width:1px;
        height:89px;
        position: absolute;
        bottom:0px;
        -webkit-animation: fall .63s linear infinite;
        -moz-animation: fall .63s linear infinite;

}

/* animate the drops*/
@-webkit-keyframes fall {
        to {margin-top:1200px}
}
@-moz-keyframes fall {
        to {margin-top:1200px;}
}

.torrents {
}

.torrents > .torrent {
    position: relative;
    width: 100%;
    height: 25px;
    color: rgba(0,0,0,0.85);
    text-shadow: none;
    line-height: 25px;
}

.torrents > .torrent:hover {
    background-color:rgba(255,255,255,0.3);
}

.torrents > .torrent:hover > .magnet {
    display: block;
}

.torrents > .torrent > div {
    position: absolute;
    height: 100%;
}

.torrents > .torrent > .source {
    left: 0px;
    width: 90px;
    text-align:center;
    border-left: 1px solid black;
    border-right: 1px solid black;
}

.torrents > .torrent > .title {
    left: 100px;
}

.torrents > .torrent > .magnet {
    background-image: url("../images/magnet.png");
    background-size: contain;
    background-repeat: no-repeat;

    position: absolute;
    display: none;
    right: 0px;
    width: 25px;
    height: 25px;
}
