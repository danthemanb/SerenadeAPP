/*
boolean isTicking = false;          // Create a Flag to know if timer is currently active

if(isTicking){
        testTxtView.setText("Action_Down, IsTicking = 1");
        timer.stop();               // Start Singing a song
        isTicking = false;
        }
        else {
        testTxtView.setText("Action_Down, IsTicking = 0");
        timer.setBase(SystemClock.elapsedRealtime() + (guessTimeMin * 60000 + guessTimeSec * 1000));
        timer.start();                 // Game start
        isTicking = true;
        }


if(!isTicking){         // If the clock is not currently ticking, start ticking
        testTxtView.setText("Action_Up, IsTicking = 0");
        timer.setBase(SystemClock.elapsedRealtime() + (guessTimeMin * 60000 + guessTimeSec * 1000));
        timer.start();
        isTicking = true;
        }
        else{
        testTxtView.setText("Action_Up, IsTicking = 1");
        }




                TextView testTxtView = (TextView) findViewById(R.id.testTxt);



                        startButton.setOnTouchListener(new View.OnTouchListener() {
            //@SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

 */