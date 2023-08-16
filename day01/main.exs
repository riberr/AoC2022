test_input = """
1000
2000
3000

4000

5000
6000

7000
8000
9000

10000
"""

# setup
{:ok, input} = File.read("input")

# part1
ans1 =
  input
  |> String.split("\r\n\r\n", trim: true)
  |> Enum.map(fn substring ->
    substring
    |> String.split("\r\n", trim: true)
    |> Enum.map(&String.to_integer/1)
    |> Enum.sum()
  end)
  |> Enum.max()

IO.puts("part1: #{inspect(ans1)}")

# part2
ans2 =
  input
  |> String.split("\r\n\r\n", trim: true)
  |> Enum.map(fn substring ->
    substring
    |> String.split("\r\n", trim: true)
    |> Enum.map(&String.to_integer/1)
    |> Enum.sum()
  end)
  |> Enum.sort()
  |> Enum.take(-3)
  |> Enum.sum()

  IO.puts("part2: #{inspect(ans2)}")
