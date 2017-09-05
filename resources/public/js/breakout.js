"use strict";

class Breakout {
  constructor (canvas) {
    this.canvas = document.querySelector(canvas);
    this.ctx    = this.canvas.getContext("2d");

    this.cfg = this.configuration();
    this.drawBall();
    this.drawPaddle();
    this.bricksConf();
    this.drawBricks();
    this.drawScore();
    this.drawLives();
    this.collisionDetection();
    this.mouseMoveHandler();
    this.keyDownHandler();
    this.keyUpHandler();
  }

  configuration () {
    return {
      width:            this.canvas.width / 2,
      height:           this.canvas.height - 30,
      ballRadius:       5,
      paddleHeight:     10,
      paddleWidth:      40,
      paddleX:          (this.canvas.width - 75) / 2,
      brickRowCount:    10,
      brickColumnCount: 5,
      brickWidth:       33,
      brickHeight:      15,
      brickPadding:     10,
      brickOffsetTop:   30,
      brickOffsetLeft:  30,
      score:            0,
      lives:            3,
      dx:               2,
      dy:              -2,
      _rightPressed:   false,
      _leftPressed:    false,
      _bricks:          []
    };
  }

  drawBall () {
    this.ctx.beginPath();
    this.ctx.beginPath();
    this.ctx.arc(this.cfg.width, this.cfg.height, this.cfg.ballRadius, 0, Math.PI * 2);
    this.ctx.fillStyle = "#333333";
    this.ctx.fill();
    this.ctx.closePath();
  }

  drawPaddle () {
    this.ctx.beginPath();
    this.ctx.rect(this.cfg.paddleX, this.canvas.height - this.cfg.paddleHeight, this.cfg.paddleWidth, this.cfg.paddleHeight);
    this.ctx.fillStyle = "#333333";
    this.ctx.fill();
    this.ctx.closePath();
  }

  bricksConf () {
    for (let c = 0; c < this.cfg.brickColumnCount; c++) {
      this.cfg._bricks[c] = [];
      for (let r = 0; r < this.cfg.brickRowCount; r++) {
        this.cfg._bricks[c][r] = { x: 0, y: 0, status: 1 };
      }
    }
  }

  drawBricks () {
    for (let c = 0; c < this.cfg.brickColumnCount; c++) {
      for (let r = 0; r < this.cfg.brickRowCount; r++) {
        if (this.cfg._bricks[c][r].status == 1) {
          let brickX = (r * (this.cfg.brickWidth + this.cfg.brickPadding)) + this.cfg.brickOffsetLeft;
          let brickY = (c * (this.cfg.brickHeight + this.cfg.brickPadding)) + this.cfg.brickOffsetTop;
          this.cfg._bricks[c][r].x = brickX;
          this.cfg._bricks[c][r].y = brickY;
          this.ctx.beginPath();
          this.ctx.rect(brickX, brickY, this.cfg.brickWidth, this.cfg.brickHeight);
          this.ctx.fillStyle = "#333333";
          this.ctx.fill();
          this.ctx.closePath();
        }
      }
    }
  }

  drawScore () {
    this.ctx.font = "16px Anonymous Pro";
    this.ctx.fillStyle = "#33333";
    this.ctx.fillText("Очки: " + this.cfg.score, 8, 20);
  }

  drawLives () {
    this.ctx.font = "16px Anonymous Pro";
    this.ctx.fillStyle = "#333333";
    this.ctx.fillText("Жизни: "+ this.cfg.lives, this.canvas.width - 75, 20);
  }

  collisionDetection () {
    for (let c = 0; c < this.cfg.brickColumnCount; c++) {
      for (let r = 0; r < this.cfg.brickRowCount; r++) {
        let b = this.cfg._bricks[c][r];

        if (b.status === 1) {
          if (this.cfg.width > b.x &&
              this.cfg.width < b.x + this.cfg.brickWidth &&
              this.cfg.height > b.y &&
              this.cfg.height < b.y + this.cfg.brickHeight) {
            this.cfg.dy = -this.cfg.dy;
            b.status = 0;
            this.cfg.score++;

            if (this.cfg.score == this.cfg.brickRowCount * this.cfg.brickColumnCount) {
              alert("Вы победили!");
              document.location.reload();
            }
          }
        }
      }
    }
  }

  mouseMoveHandler () {
    let self = this;

    return document.addEventListener("mousemove", function (e) {
      let relativeX = e.clientX - self.canvas.offsetLeft;
      if (relativeX > 0 && relativeX < self.canvas.width) {
        self.cfg.paddleX = relativeX - self.cfg.paddleWidth / 2;
      }
    }, false);
  }

  keyDownHandler () {
    let self = this;

    return document.addEventListener("keydown", function(e) {
      if (e.keyCode == 39) {
        self.cfg._rightPressed = true;
      } else if (e.keyCode == 37) {
        self.cfg._leftPressed = true;
      }
    }, false);
  }

  keyUpHandler () {
    let self = this;

    return document.addEventListener("keyup", function (e) {
      if (e.keyCode == 39) {
        self.cfg._rightPressed = false;
      } else if (e.keyCode == 37) {
        self.cfg._leftPressed = false;
      }
    }, false);
  }

  draw () {
    this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
    this.drawBricks();
    this.drawBall();
    this.drawPaddle();
    this.drawScore();
    this.drawLives();
    this.collisionDetection();

    if (this.cfg.width + this.cfg.dx > this.canvas.width - this.cfg.ballRadius ||
        this.cfg.width + this.cfg.dx < this.cfg.ballRadius) {
      this.cfg.dx = -this.cfg.dx;
    }

    if (this.cfg.height + this.cfg.dy < this.cfg.ballRadius) {
      this.cfg.dy = -this.cfg.dy;
    } else if (this.cfg.height + this.cfg.dy > this.canvas.height - this.cfg.ballRadius) {
      if (this.cfg.width > this.cfg.paddleX && this.cfg.width < this.cfg.paddleX + this.cfg.paddleWidth) {
        this.cfg.dy = -this.cfg.dy;
      } else {
        this.cfg.lives--;
        if (!this.cfg.lives) {
          alert("Вы проиграли!");
          document.location.reload();
        } else {
          this.cfg.width = this.canvas.width / 2;
          this.cfg.height = this.canvas.height - 30;
          this.cfg.dx = 3;
          this.cfg.dy = -3;
          this.cfg.paddleX = (this.canvas.width - this.cfg.paddleWidth) / 2;
        }
      }
    }

    if (this.cfg._rightPressed && this.cfg.paddleX < this.canvas.width - this.cfg.paddleWidth) {
      this.cfg.paddleX += 7;
    } else if (this.cfg._leftPressed && this.cfg.paddleX > 0) {
      this.cfg.paddleX -= 7;
    }

    this.cfg.width += this.cfg.dx;
    this.cfg.height += this.cfg.dy;

    requestAnimationFrame(this.draw.bind(this));
  }


}

new Breakout("#breakout").draw();
