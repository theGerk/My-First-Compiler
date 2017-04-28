program foo;
var
    fee, fi, fo, fum: integer;
var
    arr: array[3:9] of real;
function test( this, is, a, example:real ) : real;
	var
	    greg, w, dorr: integer;
	begin
	    greg := not 5;
	    if greg <> 10
	    then
		w := -4
	    else
		dorr := 9
	end;
procedure proc (first, second, third: real);
	var
	    one, two: integer;
	var
	    three, four: integer;
		function test2( this, is : integer) : integer;
		var
		    greg, w, dorr: integer;
		begin
		    greg := not 5;
		    if greg <> 10
		    then
			w := -4 + 8 + 4 + 1 + 5 + 2
		    else
			dorr := 9
		end;
	begin

	    one := test2(three, four)

	end;

begin

    {testing if it's a valid assignment}
    fee := 5;

    {function test}
    arr[3] := 4 - test(fee * 1.0,fi - 10 * 255.0,arr[9], 0.3284);

    {array test}
    arr[fee - 9] := 10.58;
    arr[9] := 5 * 1.0;

    {procedure test}
    proc(fee*1.0,fi*0.1,arr[9]);

    {assignment/expression test}
    fi := 5;
    fo := 3 * fee + fi + 4 + 6 + 7 + 5 + 0 + 1;

    {if statement test}
    if fo < 13
    then
        fo := 13
    else
        fo := 26;

    {while Statement test}
    while fo > 0
    do
    begin
        fo := fo + 5
    end
end
.
