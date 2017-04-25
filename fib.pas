{use all nodes!}
program ID;
var fibs : array[-2 : 100] of real;
procedure populate;
	var iter : integer;
	function sum(a, b: real): real;
		begin
			sum := a + b
		end;
	begin
		iter := 0;
		read(fibs[-2]);
		read(fibs[-1]);
		while iter <= 100 do fibs[iter] := sum(fibs[iter - 1], fibs[iter - 2])
	end;
procedure r;
	var iter : integer;
	procedure r1;
		begin
			write(fibs[iter]);
			iter := iter + 1
		end;
	begin
		iter := -2;
		while iter <= 100 do r1
	end;
begin
	populate;
	r
end
.
