package com.example.calculateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
     Button[] btn = new Button[10];
     EditText input;
     TextView _drg;
     Button div;
     Button mul;
     Button sub;
     Button add;
     Button equal;
     Button sin;
     Button cos;
     Button log;
     Button square;
     Button bksp;
     Button left;
     Button right;
     Button dot;
     Button drg;
     Button c;
     Button Switch_button;
    public String str_old;
    public String str_new;
    public boolean vbegin = true;
    public boolean drg_flag = true;
    public double pi = 4.0D * Math.atan(1.0D);
    public boolean equals_flag = true;
    private View.OnClickListener actionPerformed = new View.OnClickListener() {
        public void onClick(View v) {
            String command = ((Button) v).getText().toString();
            String str = MainActivity.this.input.getText().toString();
            if (!MainActivity.this.equals_flag && "0123456789.()sincoslog+-×÷√^".indexOf(command) != -1) {
                if (!MainActivity.this.right(str)) {
                    MainActivity.this.input.setText("0");
                    MainActivity.this.vbegin = true;
                } else if ("+-×÷√^)".indexOf(command) != -1) {
                    MainActivity.this.vbegin = false;
                }
                MainActivity.this.equals_flag = true;
            }
            if ("0123456789.()sincoslog+-×÷√^".indexOf(command) != -1) {
                MainActivity.this.print(command);
            } else if (command.compareTo("DRG") == 0) {
                if (MainActivity.this.drg_flag) {
                    MainActivity.this.drg_flag = false;
                    MainActivity.this._drg.setText("   弧度");
                } else {
                    MainActivity.this.drg_flag = true;
                    MainActivity.this._drg.setText("   角度");
                }
            } else if (command.compareTo("Bksp") == 0 && MainActivity.this.equals_flag) {
                if (MainActivity.this.TTO(str) == 3) {
                    if (str.length() > 3) {
                        MainActivity.this.input.setText(str.substring(0, str.length() - 3));
                    } else if (str.length() == 3) {
                        MainActivity.this.input.setText("0");
                        MainActivity.this.vbegin = true;
                    }
                } else if (MainActivity.this.TTO(str) == 2) {
                    if (str.length() > 2) {
                        MainActivity.this.input.setText(str.substring(0, str.length() - 2));
                    } else if (str.length() == 2) {
                        MainActivity.this.input.setText("0");
                        MainActivity.this.vbegin = true;
                    }
                } else if (MainActivity.this.TTO(str) == 1) {
                    if (MainActivity.this.right(str)) {
                        if (str.length() > 1) {
                            MainActivity.this.input.setText(str.substring(0, str.length() - 1));
                        } else if (str.length() == 1) {
                            MainActivity.this.input.setText("0");
                            MainActivity.this.vbegin = true;
                        }
                    } else {
                        MainActivity.this.input.setText("0");
                        MainActivity.this.vbegin = true;
                    }
                }

                if (MainActivity.this.input.getText().toString().compareTo("-") == 0 || !MainActivity.this.equals_flag) {
                    MainActivity.this.input.setText("0");
                    MainActivity.this.vbegin = true;
                }
            } else if (command.compareTo("Bksp") == 0 && !MainActivity.this.equals_flag) {
                MainActivity.this.input.setText("0");
                MainActivity.this.vbegin = true;
            } else if (command.compareTo("C") == 0) {
                MainActivity.this.input.setText("0");
                MainActivity.this.vbegin = true;
                MainActivity.this.equals_flag = true;
            } else if (command.compareTo("=") == 0&& MainActivity.this.right(str) && MainActivity.this.equals_flag) {
                MainActivity.this.equals_flag = false;
                MainActivity.this.str_old = str;
                str = str.replaceAll("sin", "s");
                str = str.replaceAll("cos", "c");
                str = str.replaceAll("log", "g");
                MainActivity.this.vbegin = true;
                MainActivity.this.str_new = str.replaceAll("-", "-1×");
                (MainActivity.this.new calc()).process(MainActivity.this.str_new);
            }else if(command.equals("转换")){
                Intent intent=new Intent(MainActivity.this,SwitchActivity.class);
                startActivity(intent);
            }
        }
    };

    public MainActivity() {
    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.activity_main);
        this.input = (EditText)findViewById(R.id.input);
        this._drg = (TextView)findViewById(R.id._drg);
        this.btn[0] = (Button)findViewById(R.id.zero);
        this.btn[1] = (Button)findViewById(R.id.one);
        this.btn[2] = (Button)findViewById(R.id.two);
        this.btn[3] = (Button)findViewById(R.id.three);
        this.btn[4] = (Button)findViewById(R.id.four);
        this.btn[5] = (Button)findViewById(R.id.five);
        this.btn[6] = (Button)findViewById(R.id.six);
        this.btn[7] = (Button)findViewById(R.id.seven);
        this.btn[8] = (Button)findViewById(R.id.eight);
        this.btn[9] = (Button)findViewById(R.id.nine);
        this.div = (Button)findViewById(R.id.divide);
        this.mul = (Button)findViewById(R.id.mul);
        this.sub = (Button)findViewById(R.id.sub);
        this.add = (Button)findViewById(R.id.add);
        this.equal = (Button)findViewById(R.id.equal);
        this.sin = (Button)findViewById(R.id.sin);
        this.cos = (Button)findViewById(R.id.cos);
        this.log = (Button)findViewById(R.id.log);
        this.square = (Button)findViewById(R.id.square);
        this.bksp = (Button)findViewById(R.id.bksp);
        this.left = (Button)findViewById(R.id.left);
        this.right = (Button)findViewById(R.id.right);
        this.dot = (Button)findViewById(R.id.dot);
        this.drg = (Button)findViewById(R.id.drg);
        this.c = (Button)findViewById(R.id.c);
        this.Switch_button=(Button)findViewById(R.id.switch_button);

        for (int i = 0; i < 10; ++i) {
            this.btn[i].setOnClickListener(this.actionPerformed);
        }

        this.div.setOnClickListener(this.actionPerformed);
        this.mul.setOnClickListener(this.actionPerformed);
        this.sub.setOnClickListener(this.actionPerformed);
        this.add.setOnClickListener(this.actionPerformed);
        this.equal.setOnClickListener(this.actionPerformed);
        this.sin.setOnClickListener(this.actionPerformed);
        this.cos.setOnClickListener(this.actionPerformed);
        this.log.setOnClickListener(this.actionPerformed);
        this.square.setOnClickListener(this.actionPerformed);
        this.bksp.setOnClickListener(this.actionPerformed);
        this.left.setOnClickListener(this.actionPerformed);
        this.right.setOnClickListener(this.actionPerformed);
        this.dot.setOnClickListener(this.actionPerformed);
        this.drg.setOnClickListener(this.actionPerformed);
        this.c.setOnClickListener(this.actionPerformed);
        this.Switch_button.setOnClickListener(this.actionPerformed);
    }

    private void print(String str) {
        if (this.vbegin) {
            this.input.setText(str);
        } else {
            this.input.append(str);
        }

        this.vbegin = false;
    }

    private boolean right(String str) {
        boolean i = false;

        int var3;
        for (var3 = 0; var3 < str.length() && (str.charAt(var3) == 48 || str.charAt(var3) == 49
                || str.charAt(var3) == 50 || str.charAt(var3) == 51 || str.charAt(var3) == 52
                || str.charAt(var3) == 53 || str.charAt(var3) == 54 || str.charAt(var3) == 55
                || str.charAt(var3) == 56 || str.charAt(var3) == 57 || str.charAt(var3) == 46
                || str.charAt(var3) == 45 || str.charAt(var3) == 43 || str.charAt(var3) == 215
                || str.charAt(var3) == 247 || str.charAt(var3) == 8730 || str.charAt(var3) == 94
                || str.charAt(var3) == 115 || str.charAt(var3) == 105 || str.charAt(var3) == 110
                || str.charAt(var3) == 99 || str.charAt(var3) == 111 || str.charAt(var3) == 116
                || str.charAt(var3) == 97 || str.charAt(var3) == 108 || str.charAt(var3) == 103
                || str.charAt(var3) == 40 || str.charAt(var3) == 41 || str.charAt(var3) == 33); ++var3) {
            ;
        }

        return var3 == str.length();
    }

    /*
     * 检测函数，返回值为3、2、1  表示应当一次删除几个？  Three+Two+One = TTO
     * 为Bksp按钮的删除方式提供依据
     * 返回3，表示str尾部为sin、cos、log中的一个，应当一次删除3个
     * 返回2，表示str尾部为中的一个，应当一次删除2个
     * 返回1，表示为除返回3、2外的所有情况，只需删除一个（包含非法字符时要另外考虑：应清屏）
     */
    private int TTO(String str) {
        return (str.charAt(str.length() - 1) != 110 || str.charAt(str.length() - 2) != 105
                || str.charAt(str.length() - 3) != 115) && (str.charAt(str.length() - 1) != 115
                || str.charAt(str.length() - 2) != 111 || str.charAt(str.length() - 3) != 99) && (str.charAt(str.length() - 1) != 110
                || str.charAt(str.length() - 2) != 97 || str.charAt(str.length() - 3) != 116) && (str.charAt(str.length() - 1) != 103
                || str.charAt(str.length() - 2) != 111 || str.charAt(str.length() - 3) != 108) ? ((str.charAt(str.length() - 1) != 110
                || str.charAt(str.length() - 2) != 108) && (str.charAt(str.length() - 1) != 33 || str.charAt(str.length() - 2) != 110) ? 1 : 2) : 3;
    }
    /*
     * 整个计算核心，只要将表达式的整个字符串传入calc().process()就可以实行计算了
     * 算法包括以下几部分：
     * 1、计算部分           process(String str)  当然，这是建立在查错无错误的情况下
     * 2、数据格式化      FP(double n)         使数据有相当的精确度
     */
    public class calc {
        final int MAXLEN = 500;

        /*
             * 计算表达式
             * 从左向右扫描，数字入number栈，运算符入operator栈
             * +-基本优先级为1，×÷基本优先级为2，log sin cos基本优先级为3，√^基本优先级为4
             * 括号内层运算符比外层同级运算符优先级高4
             * 当前运算符优先级高于栈顶压栈，低于栈顶弹出一个运算符与两个数进行运算
             * 重复直到当前运算符大于栈顶
             * 扫描完后对剩下的运算符与数字依次计算
             */
        public calc() {
        }

        public void process(String str) {
            int weightPlus = 0;
            int topOp = 0;
            int topNum = 0;
            byte flag = 1;
            boolean weightTemp = false;
            int[] weight = new int[500];
            double[] number = new double[500];
            char[] operator = new char[500];
            String expression = str;
            StringTokenizer expToken = new StringTokenizer(str, "+-×÷()sctgl√^");

            for (int i = 0; i < expression.length(); ++i) {
                char ch = expression.charAt(i);
                if (i == 0) {
                    if (ch == 45) {
                        flag = -1;
                    }
                } else if (expression.charAt(i - 1) == 40 && ch == 45) {
                    flag = -1;
                }

                if (ch <= 57 && ch >= 48 || ch == 46 || ch == 69) {
                    String num = expToken.nextToken();
                    char ch_gai = ch;
                    Log.e("结果", ch + "--->" + i);

                    while (i < expression.length() && (ch_gai <= 57 && ch_gai >= 48 || ch_gai == 46 || ch_gai == 69)) {
                        ch_gai = expression.charAt(i++);
                        Log.e("结果", "i的值为：" + i);
                    }

                    if (i >= expression.length()) {
                        --i;
                    } else {
                        i -= 2;
                    }

                    if (num.compareTo(".") == 0) {
                        number[topNum++] = 0.0D;
                    } else {
                        number[topNum++] = Double.parseDouble(num) * (double) flag;
                        flag = 1;
                    }
                }

                if (ch == 40) {
                    weightPlus += 4;
                }

                if (ch == 41) {
                    weightPlus -= 4;
                }

                if (ch == 45 && flag == 1 || ch == 43 || ch == 215 || ch == 247 || ch == 115
                        || ch == 99 || ch == 116 || ch == 103 || ch == 108 || ch == 33 || ch == 8730 || ch == 94) {
                    int var16;
                    switch (ch) {
                        case 'c':
                        case 'g':
                        case 'l':
                        case 's':
                        case 't':
                            var16 = 3 + weightPlus;
                            break;
                        case '+':
                        case '-':
                            var16 = 1 + weightPlus;
                            break;
                        case '×':
                        case '÷':
                            var16 = 2 + weightPlus;
                            break;
                        default:
                            var16 = 4 + weightPlus;
                    }

                    if (topOp != 0 && weight[topOp - 1] >= var16) {
                        while (topOp > 0 && weight[topOp - 1] >= var16) {
                            switch (operator[topOp - 1]) {
                                case '+':
                                    number[topNum - 2] += number[topNum - 1];
                                    break;
                                case '-':
                                    number[topNum - 2] -= number[topNum - 1];
                                    break;
                                case '^':
                                    number[topNum - 2] = Math.pow(number[topNum - 2], number[topNum - 1]);
                                    break;
                                case 'c':
                                    if (MainActivity.this.drg_flag) {
                                        number[topNum - 1] = Math.cos(number[topNum - 1] / 180.0D * MainActivity.this.pi);
                                    } else {
                                        number[topNum - 1] = Math.cos(number[topNum - 1]);
                                    }

                                    ++topNum;
                                    break;
                                case 'g':
                                    if (number[topNum - 1] <= 0.0D) {
                                        this.showError(2, MainActivity.this.str_old);
                                        return;
                                    }

                                    number[topNum - 1] = Math.log10(number[topNum - 1]);
                                    ++topNum;
                                    break;
                                case 'l':
                                    if (number[topNum - 1] <= 0.0D) {
                                        this.showError(2, MainActivity.this.str_old);
                                        return;
                                    }

                                    number[topNum - 1] = Math.log(number[topNum - 1]);
                                    ++topNum;
                                    break;
                                case 's':
                                    if (MainActivity.this.drg_flag) {
                                        number[topNum - 1] = Math.sin(number[topNum - 1] / 180.0D * MainActivity.this.pi);
                                    } else {
                                        number[topNum - 1] = Math.sin(number[topNum - 1]);
                                    }

                                    ++topNum;
                                    break;
                                case 't':
                                    if (MainActivity.this.drg_flag) {
                                        if (Math.abs(number[topNum - 1]) / 90.0D % 2.0D == 1.0D) {
                                            this.showError(2, MainActivity.this.str_old);
                                            return;
                                        }

                                        number[topNum - 1] = Math.tan(number[topNum - 1] / 180.0D * MainActivity.this.pi);
                                    } else {
                                        if (Math.abs(number[topNum - 1]) / (MainActivity.this.pi / 2.0D) % 2.0D == 1.0D) {
                                            this.showError(2, MainActivity.this.str_old);
                                            return;
                                        }

                                        number[topNum - 1] = Math.tan(number[topNum - 1]);
                                    }

                                    ++topNum;
                                    break;
                                case '×':
                                    number[topNum - 2] *= number[topNum - 1];
                                    break;
                                case '÷':
                                    if (number[topNum - 1] == 0.0D) {
                                        this.showError(1, MainActivity.this.str_old);
                                        return;
                                    }

                                    number[topNum - 2] /= number[topNum - 1];
                                    break;
                                case '√':
                                    if (number[topNum - 1] == 0.0D || number[topNum - 2] < 0.0D && number[topNum - 1] % 2.0D == 0.0D) {
                                        this.showError(2, MainActivity.this.str_old);
                                        return;
                                    }

                                    number[topNum - 2] = Math.pow(number[topNum - 2], 1.0D / number[topNum - 1]);
                            }

                            --topNum;
                            --topOp;
                        }

                        weight[topOp] = var16;
                        operator[topOp] = ch;
                        ++topOp;
                    } else {
                        weight[topOp] = var16;
                        operator[topOp] = ch;
                        ++topOp;
                    }
                }
            }

            while (topOp > 0) {
                switch (operator[topOp - 1]) {
                    case '+':
                        number[topNum - 2] += number[topNum - 1];
                        break;
                    case '-':
                        number[topNum - 2] -= number[topNum - 1];
                        break;
                    case '^':
                        number[topNum - 2] = Math.pow(number[topNum - 2], number[topNum - 1]);
                        break;
                    case 'c':
                        if (MainActivity.this.drg_flag) {
                            number[topNum - 1] = Math.cos(number[topNum - 1] / 180.0D * MainActivity.this.pi);
                        } else {
                            number[topNum - 1] = Math.cos(number[topNum - 1]);
                        }

                        ++topNum;
                        break;
                    case 'g':
                        if (number[topNum - 1] <= 0.0D) {
                            this.showError(2, MainActivity.this.str_old);
                            return;
                        }

                        number[topNum - 1] = Math.log10(number[topNum - 1]);
                        ++topNum;
                        break;
                    case 'l':
                        if (number[topNum - 1] <= 0.0D) {
                            this.showError(2, MainActivity.this.str_old);
                            return;
                        }

                        number[topNum - 1] = Math.log(number[topNum - 1]);
                        ++topNum;
                        break;
                    case 's':
                        if (MainActivity.this.drg_flag) {
                            number[topNum - 1] = Math.sin(number[topNum - 1] / 180.0D * MainActivity.this.pi);
                        } else {
                            number[topNum - 1] = Math.sin(number[topNum - 1]);
                        }

                        ++topNum;
                        break;
                    case '×':
                        number[topNum - 2] *= number[topNum - 1];
                        break;
                    case '÷':
                        if (number[topNum - 1] == 0.0D) {
                            this.showError(1, MainActivity.this.str_old);
                            return;
                        }

                        number[topNum - 2] /= number[topNum - 1];
                        break;
                    case '√':
                        if (number[topNum - 1] == 0.0D || number[topNum - 2] < 0.0D && number[topNum - 1] % 2.0D == 0.0D) {
                            this.showError(2, MainActivity.this.str_old);
                            return;
                        }

                        number[topNum - 2] = Math.pow(number[topNum - 2], 1.0D / number[topNum - 1]);
                }

                --topNum;
                --topOp;
            }

            if (number[0] > 7.3E306D) {
                this.showError(3, MainActivity.this.str_old);
            } else {
                MainActivity.this.input.setText(String.valueOf(this.FP(number[0])));
            }
        }

        /*
             * FP = floating point 控制小数位数，达到精度
             * 否则会出现 0.6-0.2=0.39999999999999997的情况，用FP即可解决，使得数为0.4
             * 本格式精度为15位
             */
        public double FP(double n) {
            DecimalFormat format = new DecimalFormat("0.#############");
            return Double.parseDouble(format.format(n));
        }
        /*
            * 错误提示，按了"="之后，若计算式在process()过程中，出现错误，则进行提示
            */
        public void showError(int code, String str) {
            String message = "";
            switch (code) {
                case 1:
                    message = "零不能作除数";
                    break;
                case 2:
                    message = "函数格式错误";
                    break;
                case 3:
                    message = "值太大了，超出范围";
            }

            MainActivity.this.input.setText("\"" + str + "\"" + ": " + message);
        }
    }
}
