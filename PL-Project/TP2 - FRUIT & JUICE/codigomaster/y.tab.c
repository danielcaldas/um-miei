/* A Bison parser, made by GNU Bison 2.5.  */

/* Bison implementation for Yacc-like parsers in C
   
      Copyright (C) 1984, 1989-1990, 2000-2011 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "2.5"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1

/* Using locations.  */
#define YYLSP_NEEDED 0



/* Copy the first part of user declarations.  */

/* Line 268 of yacc.c  */
#line 1 "fruitjuice.y"

   #include <stdio.h>
   #include <stdlib.h>
   #include <string.h>
   #include "const.h"
   #include "hash.h"

   extern int yylex();
   int yyerror(char* s);

   // Variáveis globais
   FILE *f; // Ficheiro que irá conter o resultado final do programa, o assembly gerado
   
   char *array_nome; // Nome do array
   
   /* A quando da declaracao de um array com elementos, 
	esta variavel faz cache desses elementos para posterior 
	insercao na tabela de simbolos */
   int *array;
   
   /* A quando da declaracao de um array com elementos, 
	representa o numero de elementos no array */
   int nelems;
   
   /* Sempre que é inicializado um array com valores, 
	size_array fica com o tamanho desse array */
   int size_array;
   
   typedef struct stackCondicao{
	   int valor;
	   struct stackCondicao *prox;
   } *SC;
   
   //auxiliares para contagem dos if's
   int prof = 0;
   SC stackIf;
   
   //auxiliares para contagem dos ciclos
   int ciclo=1;
   int fciclo=1;

   //insere o nivel atual na cabeça da lista auxif
   void insertCond(){
	   prof++;
	   SC novo;
	   novo = (SC)malloc(sizeof(struct stackCondicao));
	   novo->valor = prof;
	   novo->prox = NULL;
	   
	   if( stackIf == NULL) stackIf = novo;
	   else {
		   novo->prox = stackIf;
		   stackIf = novo;
	   }
	   
   }
   
   // Remove a cabeça da stack de condicoes
   void removeHead(){
	   SC novo;
	   novo = stackIf->prox;
	   free(stackIf);
	   stackIf = novo;
   }
   
   // Obtem o nivel que estiver na cabeça da stack de condicoes
   int getHead(){
	   if(stackIf == NULL) return -1;
	   else return(stackIf->valor);
   }
   


/* Line 268 of yacc.c  */
#line 145 "y.tab.c"

/* Enabling traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* Enabling the token table.  */
#ifndef YYTOKEN_TABLE
# define YYTOKEN_TABLE 0
#endif


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     NOME = 258,
     NUMERO = 259,
     FJBEGIN = 260,
     FJEND = 261,
     FRUIT = 262,
     JUICE = 263,
     IF = 264,
     ELSE = 265,
     WHILE = 266,
     FOR = 267,
     WOUT = 268,
     RINP = 269,
     ERRO = 270
   };
#endif
/* Tokens.  */
#define NOME 258
#define NUMERO 259
#define FJBEGIN 260
#define FJEND 261
#define FRUIT 262
#define JUICE 263
#define IF 264
#define ELSE 265
#define WHILE 266
#define FOR 267
#define WOUT 268
#define RINP 269
#define ERRO 270




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
{

/* Line 293 of yacc.c  */
#line 74 "fruitjuice.y"

	int vint;
	char* vstr;
	char vchar;



/* Line 293 of yacc.c  */
#line 219 "y.tab.c"
} YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif


/* Copy the second part of user declarations.  */


/* Line 343 of yacc.c  */
#line 231 "y.tab.c"

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#elif (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
typedef signed char yytype_int8;
#else
typedef short int yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(msgid) dgettext ("bison-runtime", msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(msgid) msgid
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(e) ((void) (e))
#else
# define YYUSE(e) /* empty */
#endif

/* Identity function, used to suppress warnings about constant conditions.  */
#ifndef lint
# define YYID(n) (n)
#else
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static int
YYID (int yyi)
#else
static int
YYID (yyi)
    int yyi;
#endif
{
  return yyi;
}
#endif

#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined EXIT_SUCCESS && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#     ifndef EXIT_SUCCESS
#      define EXIT_SUCCESS 0
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's `empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (YYID (0))
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
	     && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
	 || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)				\
    do									\
      {									\
	YYSIZE_T yynewbytes;						\
	YYCOPY (&yyptr->Stack_alloc, Stack, yysize);			\
	Stack = &yyptr->Stack_alloc;					\
	yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
	yyptr += yynewbytes / sizeof (*yyptr);				\
      }									\
    while (YYID (0))

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from FROM to TO.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(To, From, Count) \
      __builtin_memcpy (To, From, (Count) * sizeof (*(From)))
#  else
#   define YYCOPY(To, From, Count)		\
      do					\
	{					\
	  YYSIZE_T yyi;				\
	  for (yyi = 0; yyi < (Count); yyi++)	\
	    (To)[yyi] = (From)[yyi];		\
	}					\
      while (YYID (0))
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  5
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   145

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  35
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  34
/* YYNRULES -- Number of rules.  */
#define YYNRULES  70
/* YYNRULES -- Number of states.  */
#define YYNSTATES  151

/* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   270

#define YYTRANSLATE(YYX)						\
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[YYLEX] -- Bison symbol number corresponding to YYLEX.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    27,     2,     2,     2,    33,    34,     2,
      23,    24,    31,    28,    17,    29,     2,    32,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,    16,
      25,    18,    26,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    19,     2,    20,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    21,    30,    22,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15
};

#if YYDEBUG
/* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
   YYRHS.  */
static const yytype_uint8 yyprhs[] =
{
       0,     0,     3,     7,     8,     9,    16,    17,    18,    20,
      24,    27,    29,    33,    35,    39,    44,    51,    54,    58,
      60,    64,    66,    67,    69,    72,    75,    77,    79,    82,
      86,    87,    95,   100,   108,   109,   110,   111,   126,   127,
     128,   138,   142,   147,   151,   156,   161,   166,   171,   172,
     173,   183,   184,   185,   199,   201,   205,   207,   209,   214,
     216,   218,   220,   222,   224,   226,   228,   230,   232,   237,
     242
};

/* YYRHS -- A `-1'-separated list of the rules' RHS.  */
static const yytype_int8 yyrhs[] =
{
      36,     0,    -1,     5,    37,     6,    -1,    -1,    -1,     7,
      38,    40,     8,    39,    46,    -1,    -1,    -1,    41,    -1,
      41,    42,    16,    -1,    42,    16,    -1,    43,    -1,    42,
      17,    43,    -1,     3,    -1,     3,    18,     4,    -1,     3,
      19,     4,    20,    -1,     3,    19,     4,    20,    18,    44,
      -1,    21,    22,    -1,    21,    45,    22,    -1,     4,    -1,
      45,    17,     4,    -1,    47,    -1,    -1,    48,    -1,    47,
      48,    -1,    68,    16,    -1,    51,    -1,    58,    -1,    49,
      16,    -1,     3,    18,    63,    -1,    -1,     3,    19,     4,
      20,    18,    50,    63,    -1,    14,    23,     3,    24,    -1,
      14,    23,     3,    19,     4,    20,    24,    -1,    -1,    -1,
      -1,     9,    52,    23,    57,    24,    53,    21,    46,    22,
      54,    10,    21,    46,    22,    -1,    -1,    -1,     9,    55,
      23,    57,    24,    56,    21,    46,    22,    -1,    63,    25,
      63,    -1,    63,    18,    25,    63,    -1,    63,    26,    63,
      -1,    63,    26,    18,    63,    -1,    63,    18,    18,    63,
      -1,    63,    27,    18,    63,    -1,    27,    23,    57,    24,
      -1,    -1,    -1,    59,    11,    23,    57,    24,    60,    21,
      46,    22,    -1,    -1,    -1,    61,    12,    23,    49,    16,
      57,    62,    16,    49,    24,    21,    46,    22,    -1,    64,
      -1,    63,    65,    64,    -1,     4,    -1,     3,    -1,     3,
      19,     4,    20,    -1,    66,    -1,    67,    -1,    28,    -1,
      29,    -1,    30,    -1,    31,    -1,    32,    -1,    33,    -1,
      34,    -1,    13,    23,     4,    24,    -1,    13,    23,     3,
      24,    -1,    13,    23,     3,    19,     4,    20,    24,    -1
};

/* YYRLINE[YYN] -- source line where rule number YYN was defined.  */
static const yytype_uint16 yyrline[] =
{
       0,    88,    88,    90,    91,    90,    92,    94,    95,    97,
      98,   100,   101,   103,   112,   121,   130,   164,   165,   167,
     176,   182,   183,   185,   186,   188,   189,   190,   191,   193,
     199,   199,   209,   215,   230,   231,   232,   230,   238,   239,
     238,   243,   244,   245,   246,   247,   248,   249,   251,   253,
     251,   262,   264,   262,   273,   274,   306,   307,   311,   324,
     325,   327,   328,   329,   331,   332,   333,   334,   336,   342,
     352
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || YYTOKEN_TABLE
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "NOME", "NUMERO", "FJBEGIN", "FJEND",
  "FRUIT", "JUICE", "IF", "ELSE", "WHILE", "FOR", "WOUT", "RINP", "ERRO",
  "';'", "','", "'='", "'['", "']'", "'{'", "'}'", "'('", "')'", "'<'",
  "'>'", "'!'", "'+'", "'-'", "'|'", "'*'", "'/'", "'%'", "'&'", "$accept",
  "Programa", "Linhas", "$@1", "$@2", "Declaracoes", "ListaDeclaracoes",
  "ListaVar", "Var", "ListaNum", "ListaNums", "Codigo", "Lines", "Linha",
  "Atribuicao", "$@3", "If", "$@4", "$@5", "$@6", "$@7", "$@8", "Condicao",
  "Ciclo", "$@9", "$@10", "$@11", "$@12", "Expr", "Valor", "Oper",
  "OperAdicao", "OperMultiplicacao", "Print", 0
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[YYLEX-NUM] -- Internal token number corresponding to
   token YYLEX-NUM.  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,    59,    44,    61,    91,
      93,   123,   125,    40,    41,    60,    62,    33,    43,    45,
     124,    42,    47,    37,    38
};
# endif

/* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint8 yyr1[] =
{
       0,    35,    36,    38,    39,    37,    37,    40,    40,    41,
      41,    42,    42,    43,    43,    43,    43,    44,    44,    45,
      45,    46,    46,    47,    47,    48,    48,    48,    48,    49,
      50,    49,    49,    49,    52,    53,    54,    51,    55,    56,
      51,    57,    57,    57,    57,    57,    57,    57,    59,    60,
      58,    61,    62,    58,    63,    63,    64,    64,    64,    65,
      65,    66,    66,    66,    67,    67,    67,    67,    68,    68,
      68
};

/* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,     3,     0,     0,     6,     0,     0,     1,     3,
       2,     1,     3,     1,     3,     4,     6,     2,     3,     1,
       3,     1,     0,     1,     2,     2,     1,     1,     2,     3,
       0,     7,     4,     7,     0,     0,     0,    14,     0,     0,
       9,     3,     4,     3,     4,     4,     4,     4,     0,     0,
       9,     0,     0,    13,     1,     3,     1,     1,     4,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     4,     4,
       7
};

/* YYDEFACT[STATE-NAME] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE doesn't specify something else to do.  Zero
   means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
       0,     6,     0,     3,     0,     1,     7,     2,    13,     0,
       8,     0,    11,     0,     0,     4,     0,    10,     0,    14,
       0,    22,     9,    12,    15,     0,    34,     0,     0,     5,
      21,    23,     0,    26,    27,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,    24,    28,     0,     0,    25,
       0,    16,    57,    56,    29,    54,     0,     0,     0,     0,
       0,     0,     0,     0,    19,    17,     0,     0,    61,    62,
      63,    64,    65,    66,    67,     0,    59,    60,     0,     0,
       0,     0,     0,     0,    69,    68,     0,    32,     0,     0,
       0,    18,     0,    55,    30,     0,    35,     0,     0,     0,
       0,    39,     0,     0,    49,     0,    20,    58,     0,     0,
       0,     0,     0,    41,     0,    43,     0,     0,     0,     0,
       0,    52,    31,    47,    22,    45,    42,    44,    46,    22,
      70,    33,    22,     0,     0,     0,     0,     0,    36,    40,
      50,     0,     0,     0,     0,    22,    22,     0,     0,    53,
      37
};

/* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int16 yydefgoto[] =
{
      -1,     2,     4,     6,    21,     9,    10,    11,    12,    51,
      66,    29,    30,    31,    32,   108,    33,    41,   110,   142,
      42,   117,    80,    34,    35,   120,    36,   133,    81,    55,
      75,    76,    77,    37
};

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
#define YYPACT_NINF -118
static const yytype_int8 yypact[] =
{
      20,    10,    36,  -118,    35,  -118,    44,  -118,    -5,    60,
      44,    27,  -118,    70,    76,  -118,    29,  -118,    44,  -118,
      61,     7,  -118,  -118,    64,    14,  -118,    62,    63,  -118,
       7,  -118,    68,  -118,  -118,    72,    77,    71,    69,    67,
      84,    73,    74,    75,    88,  -118,  -118,    78,    79,  -118,
       2,  -118,    80,  -118,    33,  -118,    83,     0,     0,    15,
      81,    16,     0,     8,  -118,  -118,     9,    89,  -118,  -118,
    -118,  -118,  -118,  -118,  -118,    67,  -118,  -118,    82,    85,
      86,    24,    87,    90,  -118,  -118,    91,  -118,    92,    93,
      94,  -118,    95,  -118,  -118,     0,  -118,    12,    67,     5,
      96,  -118,    97,    98,  -118,     0,  -118,  -118,    67,    99,
     100,    67,    67,    33,    67,    33,    67,   101,   102,   103,
     104,  -118,    33,  -118,     7,    33,    33,    33,    33,     7,
    -118,  -118,     7,   108,   106,   107,   109,     8,  -118,  -118,
    -118,   110,   120,   111,   112,     7,     7,   113,   114,  -118,
    -118
};

/* YYPGOTO[NTERM-NUM].  */
static const yytype_int8 yypgoto[] =
{
    -118,  -118,  -118,  -118,  -118,  -118,  -118,   127,   121,  -118,
    -118,  -117,  -118,   115,   -61,  -118,  -118,  -118,  -118,  -118,
    -118,  -118,   -57,  -118,  -118,  -118,  -118,  -118,   -39,    17,
    -118,  -118,  -118,  -118
};

/* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule which
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
#define YYTABLE_NINF -52
static const yytype_int16 yytable[] =
{
      54,    82,    89,    52,    53,    88,    64,   134,    52,    53,
      25,    25,   135,    13,    14,   136,    26,     3,   -48,   -51,
      27,    28,    28,   114,    65,     1,    90,    79,   147,   148,
     111,    91,    39,    40,    83,    86,     5,   112,   109,    84,
      87,     7,    97,    17,    18,    22,    18,     8,   121,    98,
      99,   100,    68,    69,    70,    71,    72,    73,    74,   113,
     115,    68,    69,    70,    71,    72,    73,    74,    15,   122,
      52,    53,   125,   126,    19,   127,   141,   128,    59,    60,
      20,    24,    38,    47,    46,    43,    44,    49,    56,    48,
      50,    61,    93,    92,   102,   103,    57,    58,   106,    67,
      94,    62,    63,    78,     0,    85,     0,     0,    95,   105,
      96,   101,     0,     0,   116,   107,   104,   118,   119,     0,
       0,   124,   129,   123,   137,   132,   130,   131,   138,   139,
     144,   140,   145,   146,   143,   149,   150,    16,     0,    23,
       0,     0,     0,     0,     0,    45
};

#define yypact_value_is_default(yystate) \
  ((yystate) == (-118))

#define yytable_value_is_error(yytable_value) \
  YYID (0)

static const yytype_int16 yycheck[] =
{
      39,    58,    63,     3,     4,    62,     4,   124,     3,     4,
       3,     3,   129,    18,    19,   132,     9,     7,    11,    12,
      13,    14,    14,    18,    22,     5,    17,    27,   145,   146,
      18,    22,    18,    19,    19,    19,     0,    25,    95,    24,
      24,     6,    18,    16,    17,    16,    17,     3,   105,    25,
      26,    27,    28,    29,    30,    31,    32,    33,    34,    98,
      99,    28,    29,    30,    31,    32,    33,    34,     8,   108,
       3,     4,   111,   112,     4,   114,   137,   116,     3,     4,
       4,    20,    18,    11,    16,    23,    23,    16,     4,    12,
      21,     3,    75,     4,     4,     4,    23,    23,     4,    19,
      18,    23,    23,    20,    -1,    24,    -1,    -1,    23,    16,
      24,    24,    -1,    -1,    18,    20,    24,    20,    20,    -1,
      -1,    21,    21,    24,    16,    21,    24,    24,    22,    22,
      10,    22,    21,    21,    24,    22,    22,    10,    -1,    18,
      -1,    -1,    -1,    -1,    -1,    30
};

/* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
   symbol of state STATE-NUM.  */
static const yytype_uint8 yystos[] =
{
       0,     5,    36,     7,    37,     0,    38,     6,     3,    40,
      41,    42,    43,    18,    19,     8,    42,    16,    17,     4,
       4,    39,    16,    43,    20,     3,     9,    13,    14,    46,
      47,    48,    49,    51,    58,    59,    61,    68,    18,    18,
      19,    52,    55,    23,    23,    48,    16,    11,    12,    16,
      21,    44,     3,     4,    63,    64,     4,    23,    23,     3,
       4,     3,    23,    23,     4,    22,    45,    19,    28,    29,
      30,    31,    32,    33,    34,    65,    66,    67,    20,    27,
      57,    63,    57,    19,    24,    24,    19,    24,    57,    49,
      17,    22,     4,    64,    18,    23,    24,    18,    25,    26,
      27,    24,     4,     4,    24,    16,     4,    20,    50,    57,
      53,    18,    25,    63,    18,    63,    18,    56,    20,    20,
      60,    57,    63,    24,    21,    63,    63,    63,    63,    21,
      24,    24,    21,    62,    46,    46,    46,    16,    22,    22,
      22,    49,    54,    24,    10,    21,    21,    46,    46,    22,
      22
};

#define yyerrok		(yyerrstatus = 0)
#define yyclearin	(yychar = YYEMPTY)
#define YYEMPTY		(-2)
#define YYEOF		0

#define YYACCEPT	goto yyacceptlab
#define YYABORT		goto yyabortlab
#define YYERROR		goto yyerrorlab


/* Like YYERROR except do call yyerror.  This remains here temporarily
   to ease the transition to the new meaning of YYERROR, for GCC.
   Once GCC version 2 has supplanted version 1, this can go.  However,
   YYFAIL appears to be in use.  Nevertheless, it is formally deprecated
   in Bison 2.4.2's NEWS entry, where a plan to phase it out is
   discussed.  */

#define YYFAIL		goto yyerrlab
#if defined YYFAIL
  /* This is here to suppress warnings from the GCC cpp's
     -Wunused-macros.  Normally we don't worry about that warning, but
     some users do, and we want to make it easy for users to remove
     YYFAIL uses, which will produce warnings from Bison 2.5.  */
#endif

#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)					\
do								\
  if (yychar == YYEMPTY && yylen == 1)				\
    {								\
      yychar = (Token);						\
      yylval = (Value);						\
      YYPOPSTACK (1);						\
      goto yybackup;						\
    }								\
  else								\
    {								\
      yyerror (YY_("syntax error: cannot back up")); \
      YYERROR;							\
    }								\
while (YYID (0))


#define YYTERROR	1
#define YYERRCODE	256


/* YYLLOC_DEFAULT -- Set CURRENT to span from RHS[1] to RHS[N].
   If N is 0, then set CURRENT to the empty location which ends
   the previous symbol: RHS[0] (always defined).  */

#define YYRHSLOC(Rhs, K) ((Rhs)[K])
#ifndef YYLLOC_DEFAULT
# define YYLLOC_DEFAULT(Current, Rhs, N)				\
    do									\
      if (YYID (N))                                                    \
	{								\
	  (Current).first_line   = YYRHSLOC (Rhs, 1).first_line;	\
	  (Current).first_column = YYRHSLOC (Rhs, 1).first_column;	\
	  (Current).last_line    = YYRHSLOC (Rhs, N).last_line;		\
	  (Current).last_column  = YYRHSLOC (Rhs, N).last_column;	\
	}								\
      else								\
	{								\
	  (Current).first_line   = (Current).last_line   =		\
	    YYRHSLOC (Rhs, 0).last_line;				\
	  (Current).first_column = (Current).last_column =		\
	    YYRHSLOC (Rhs, 0).last_column;				\
	}								\
    while (YYID (0))
#endif


/* This macro is provided for backward compatibility. */

#ifndef YY_LOCATION_PRINT
# define YY_LOCATION_PRINT(File, Loc) ((void) 0)
#endif


/* YYLEX -- calling `yylex' with the right arguments.  */

#ifdef YYLEX_PARAM
# define YYLEX yylex (YYLEX_PARAM)
#else
# define YYLEX yylex ()
#endif

/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)			\
do {						\
  if (yydebug)					\
    YYFPRINTF Args;				\
} while (YYID (0))

# define YY_SYMBOL_PRINT(Title, Type, Value, Location)			  \
do {									  \
  if (yydebug)								  \
    {									  \
      YYFPRINTF (stderr, "%s ", Title);					  \
      yy_symbol_print (stderr,						  \
		  Type, Value); \
      YYFPRINTF (stderr, "\n");						  \
    }									  \
} while (YYID (0))


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_value_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# else
  YYUSE (yyoutput);
# endif
  switch (yytype)
    {
      default:
	break;
    }
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  if (yytype < YYNTOKENS)
    YYFPRINTF (yyoutput, "token %s (", yytname[yytype]);
  else
    YYFPRINTF (yyoutput, "nterm %s (", yytname[yytype]);

  yy_symbol_value_print (yyoutput, yytype, yyvaluep);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_stack_print (yytype_int16 *yybottom, yytype_int16 *yytop)
#else
static void
yy_stack_print (yybottom, yytop)
    yytype_int16 *yybottom;
    yytype_int16 *yytop;
#endif
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)				\
do {								\
  if (yydebug)							\
    yy_stack_print ((Bottom), (Top));				\
} while (YYID (0))


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_reduce_print (YYSTYPE *yyvsp, int yyrule)
#else
static void
yy_reduce_print (yyvsp, yyrule)
    YYSTYPE *yyvsp;
    int yyrule;
#endif
{
  int yynrhs = yyr2[yyrule];
  int yyi;
  unsigned long int yylno = yyrline[yyrule];
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
	     yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr, yyrhs[yyprhs[yyrule] + yyi],
		       &(yyvsp[(yyi + 1) - (yynrhs)])
		       		       );
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)		\
do {					\
  if (yydebug)				\
    yy_reduce_print (yyvsp, Rule); \
} while (YYID (0))

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef	YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif


#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static YYSIZE_T
yystrlen (const char *yystr)
#else
static YYSIZE_T
yystrlen (yystr)
    const char *yystr;
#endif
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static char *
yystpcpy (char *yydest, const char *yysrc)
#else
static char *
yystpcpy (yydest, yysrc)
    char *yydest;
    const char *yysrc;
#endif
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
	switch (*++yyp)
	  {
	  case '\'':
	  case ',':
	    goto do_not_strip_quotes;

	  case '\\':
	    if (*++yyp != '\\')
	      goto do_not_strip_quotes;
	    /* Fall through.  */
	  default:
	    if (yyres)
	      yyres[yyn] = *yyp;
	    yyn++;
	    break;

	  case '"':
	    if (yyres)
	      yyres[yyn] = '\0';
	    return yyn;
	  }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into *YYMSG, which is of size *YYMSG_ALLOC, an error message
   about the unexpected token YYTOKEN for the state stack whose top is
   YYSSP.

   Return 0 if *YYMSG was successfully written.  Return 1 if *YYMSG is
   not large enough to hold the message.  In that case, also set
   *YYMSG_ALLOC to the required number of bytes.  Return 2 if the
   required number of bytes is too large to store.  */
static int
yysyntax_error (YYSIZE_T *yymsg_alloc, char **yymsg,
                yytype_int16 *yyssp, int yytoken)
{
  YYSIZE_T yysize0 = yytnamerr (0, yytname[yytoken]);
  YYSIZE_T yysize = yysize0;
  YYSIZE_T yysize1;
  enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
  /* Internationalized format string. */
  const char *yyformat = 0;
  /* Arguments of yyformat. */
  char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
  /* Number of reported tokens (one for the "unexpected", one per
     "expected"). */
  int yycount = 0;

  /* There are many possibilities here to consider:
     - Assume YYFAIL is not used.  It's too flawed to consider.  See
       <http://lists.gnu.org/archive/html/bison-patches/2009-12/msg00024.html>
       for details.  YYERROR is fine as it does not invoke this
       function.
     - If this state is a consistent state with a default action, then
       the only way this function was invoked is if the default action
       is an error action.  In that case, don't check for expected
       tokens because there are none.
     - The only way there can be no lookahead present (in yychar) is if
       this state is a consistent state with a default action.  Thus,
       detecting the absence of a lookahead is sufficient to determine
       that there is no unexpected or expected token to report.  In that
       case, just report a simple "syntax error".
     - Don't assume there isn't a lookahead just because this state is a
       consistent state with a default action.  There might have been a
       previous inconsistent state, consistent state with a non-default
       action, or user semantic action that manipulated yychar.
     - Of course, the expected token list depends on states to have
       correct lookahead information, and it depends on the parser not
       to perform extra reductions after fetching a lookahead from the
       scanner and before detecting a syntax error.  Thus, state merging
       (from LALR or IELR) and default reductions corrupt the expected
       token list.  However, the list is correct for canonical LR with
       one exception: it will still contain any token that will not be
       accepted due to an error action in a later state.
  */
  if (yytoken != YYEMPTY)
    {
      int yyn = yypact[*yyssp];
      yyarg[yycount++] = yytname[yytoken];
      if (!yypact_value_is_default (yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative indexes in
             YYCHECK.  In other words, skip the first -YYN actions for
             this state because they are default actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST - yyn + 1;
          int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
          int yyx;

          for (yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR
                && !yytable_value_is_error (yytable[yyx + yyn]))
              {
                if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
                  {
                    yycount = 1;
                    yysize = yysize0;
                    break;
                  }
                yyarg[yycount++] = yytname[yyx];
                yysize1 = yysize + yytnamerr (0, yytname[yyx]);
                if (! (yysize <= yysize1
                       && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
                  return 2;
                yysize = yysize1;
              }
        }
    }

  switch (yycount)
    {
# define YYCASE_(N, S)                      \
      case N:                               \
        yyformat = S;                       \
      break
      YYCASE_(0, YY_("syntax error"));
      YYCASE_(1, YY_("syntax error, unexpected %s"));
      YYCASE_(2, YY_("syntax error, unexpected %s, expecting %s"));
      YYCASE_(3, YY_("syntax error, unexpected %s, expecting %s or %s"));
      YYCASE_(4, YY_("syntax error, unexpected %s, expecting %s or %s or %s"));
      YYCASE_(5, YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s"));
# undef YYCASE_
    }

  yysize1 = yysize + yystrlen (yyformat);
  if (! (yysize <= yysize1 && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
    return 2;
  yysize = yysize1;

  if (*yymsg_alloc < yysize)
    {
      *yymsg_alloc = 2 * yysize;
      if (! (yysize <= *yymsg_alloc
             && *yymsg_alloc <= YYSTACK_ALLOC_MAXIMUM))
        *yymsg_alloc = YYSTACK_ALLOC_MAXIMUM;
      return 1;
    }

  /* Avoid sprintf, as that infringes on the user's name space.
     Don't have undefined behavior even if the translation
     produced a string with the wrong number of "%s"s.  */
  {
    char *yyp = *yymsg;
    int yyi = 0;
    while ((*yyp = *yyformat) != '\0')
      if (*yyp == '%' && yyformat[1] == 's' && yyi < yycount)
        {
          yyp += yytnamerr (yyp, yyarg[yyi++]);
          yyformat += 2;
        }
      else
        {
          yyp++;
          yyformat++;
        }
  }
  return 0;
}
#endif /* YYERROR_VERBOSE */

/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep)
#else
static void
yydestruct (yymsg, yytype, yyvaluep)
    const char *yymsg;
    int yytype;
    YYSTYPE *yyvaluep;
#endif
{
  YYUSE (yyvaluep);

  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  switch (yytype)
    {

      default:
	break;
    }
}


/* Prevent warnings from -Wmissing-prototypes.  */
#ifdef YYPARSE_PARAM
#if defined __STDC__ || defined __cplusplus
int yyparse (void *YYPARSE_PARAM);
#else
int yyparse ();
#endif
#else /* ! YYPARSE_PARAM */
#if defined __STDC__ || defined __cplusplus
int yyparse (void);
#else
int yyparse ();
#endif
#endif /* ! YYPARSE_PARAM */


/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;

/* Number of syntax errors so far.  */
int yynerrs;


/*----------.
| yyparse.  |
`----------*/

#ifdef YYPARSE_PARAM
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void *YYPARSE_PARAM)
#else
int
yyparse (YYPARSE_PARAM)
    void *YYPARSE_PARAM;
#endif
#else /* ! YYPARSE_PARAM */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void)
#else
int
yyparse ()

#endif
#endif
{
    int yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       `yyss': related to states.
       `yyvs': related to semantic values.

       Refer to the stacks thru separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yytype_int16 yyssa[YYINITDEPTH];
    yytype_int16 *yyss;
    yytype_int16 *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    YYSIZE_T yystacksize;

  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yytoken = 0;
  yyss = yyssa;
  yyvs = yyvsa;
  yystacksize = YYINITDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */

  /* Initialize stack pointers.
     Waste one element of value and location stack
     so that they stay on the same level as the state stack.
     The wasted elements are never initialized.  */
  yyssp = yyss;
  yyvsp = yyvs;

  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
	/* Give user a chance to reallocate the stack.  Use copies of
	   these so that the &'s don't force the real ones into
	   memory.  */
	YYSTYPE *yyvs1 = yyvs;
	yytype_int16 *yyss1 = yyss;

	/* Each stack pointer address is followed by the size of the
	   data in use in that stack, in bytes.  This used to be a
	   conditional around just the two extra args, but that might
	   be undefined if yyoverflow is a macro.  */
	yyoverflow (YY_("memory exhausted"),
		    &yyss1, yysize * sizeof (*yyssp),
		    &yyvs1, yysize * sizeof (*yyvsp),
		    &yystacksize);

	yyss = yyss1;
	yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
	goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
	yystacksize = YYMAXDEPTH;

      {
	yytype_int16 *yyss1 = yyss;
	union yyalloc *yyptr =
	  (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
	if (! yyptr)
	  goto yyexhaustedlab;
	YYSTACK_RELOCATE (yyss_alloc, yyss);
	YYSTACK_RELOCATE (yyvs_alloc, yyvs);
#  undef YYSTACK_RELOCATE
	if (yyss1 != yyssa)
	  YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
		  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
	YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = YYLEX;
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yytable_value_is_error (yyn))
        goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token.  */
  yychar = YYEMPTY;

  yystate = yyn;
  *++yyvsp = yylval;

  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     `$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
        case 3:

/* Line 1806 of yacc.c  */
#line 90 "fruitjuice.y"
    { initHash(); }
    break;

  case 4:

/* Line 1806 of yacc.c  */
#line 91 "fruitjuice.y"
    { fprintf(f, "START\n"); }
    break;

  case 13:

/* Line 1806 of yacc.c  */
#line 103 "fruitjuice.y"
    {
		if(containsValue((yyvsp[(1) - (1)].vstr))==1){
			fprintf(stderr, "%s %s\n", ERR_REDECLARED, (yyvsp[(1) - (1)].vstr));
		}
		else {
			insertHashVSI(INT,(yyvsp[(1) - (1)].vstr));
			fprintf(f, "PUSHI 0\n");
		}
	   }
    break;

  case 14:

/* Line 1806 of yacc.c  */
#line 112 "fruitjuice.y"
    {
		if(containsValue((yyvsp[(1) - (3)].vstr))==1){
			fprintf(stderr, "%s %s\n", ERR_REDECLARED, (yyvsp[(1) - (3)].vstr));
		}
		else {
			insertHashVCI(INT,(yyvsp[(1) - (3)].vstr),(yyvsp[(3) - (3)].vint));
			fprintf(f, "PUSHI %d\n", (yyvsp[(3) - (3)].vint));
		}
	   }
    break;

  case 15:

/* Line 1806 of yacc.c  */
#line 121 "fruitjuice.y"
    {
		if(containsValue((yyvsp[(1) - (4)].vstr))){
			fprintf(stderr, "%s %s\n", ERR_REDECLARED, (yyvsp[(1) - (4)].vstr));
		}
		else {
			insertHashASI(INT_ARRAY,(yyvsp[(1) - (4)].vstr),(yyvsp[(3) - (4)].vint));
			fprintf(f, "PUSHN %d\n", (yyvsp[(3) - (4)].vint));
		}
	   }
    break;

  case 16:

/* Line 1806 of yacc.c  */
#line 130 "fruitjuice.y"
    {
		if(containsValue((yyvsp[(1) - (6)].vstr))){
			fprintf(stderr, "%s %s\n", ERR_REDECLARED, (yyvsp[(1) - (6)].vstr));
		}
		else {
		  array_nome=(yyvsp[(1) - (6)].vstr);
		  size_array=(yyvsp[(3) - (6)].vint);

		  int i;
		  for(i=0; i<(size_array-nelems); i++){
			fprintf(f, "PUSHI 0\n");
			// Alocar espaço na stack para o array
		  }

		  if(nelems>size_array){
			fprintf(stderr, "%s %d %s %d\n", ERR_ARRAY_OVERFLOW_1, size_array, ERR_ARRAY_OVERFLOW_2, nelems);
		  }
		  else{
			if(nelems==0){
				insertHashASI(INT_ARRAY,array_nome,size_array);
			}
			else {
				insertHashACI(INT_ARRAY,array_nome,size_array,array,nelems);
				free(array);
				}
			}
			// (Re)inicializacao de variaveis auxiliares
			array=NULL;
			array_nome=NULL;
			size_array=0;
			nelems=0;
		}
	   }
    break;

  case 19:

/* Line 1806 of yacc.c  */
#line 167 "fruitjuice.y"
    {
	  // Alocar array para armazenar valores na produção ListaNums
		array = (int*) malloc(MAX_SIZE*sizeof(int));
		nelems=0;

		array[nelems] = (yyvsp[(1) - (1)].vint);	
		nelems++;
		fprintf(f, "PUSHI %d\n", (yyvsp[(1) - (1)].vint));
	   }
    break;

  case 20:

/* Line 1806 of yacc.c  */
#line 176 "fruitjuice.y"
    {
		array[nelems] = (yyvsp[(3) - (3)].vint);
		nelems++;
		fprintf(f, "PUSHI %d\n", (yyvsp[(3) - (3)].vint));
	   }
    break;

  case 29:

/* Line 1806 of yacc.c  */
#line 193 "fruitjuice.y"
    {
	if(containsValue((yyvsp[(1) - (3)].vstr)))
		fprintf(f,"STOREG %d\n",findEnderecoV((yyvsp[(1) - (3)].vstr)));
	else
		fprintf(stderr, "%s %s\n", ERR_REDECLARED, (yyvsp[(1) - (3)].vstr));
	}
    break;

  case 30:

/* Line 1806 of yacc.c  */
#line 199 "fruitjuice.y"
    {
	if(containsValue((yyvsp[(1) - (5)].vstr)) && validPos((yyvsp[(1) - (5)].vstr),(yyvsp[(3) - (5)].vint))){
		fprintf(f,"PUSHGP\n");
		fprintf(f,"PUSHI %d \n",findEnderecoA((yyvsp[(1) - (5)].vstr),(yyvsp[(3) - (5)].vint)));
		fprintf(f,"PADD\n");
		fprintf(f,"PUSHI %d\n",(yyvsp[(3) - (5)].vint));
		fprintf(f,"LOADN\n");
	} else if(!containsValue((yyvsp[(1) - (5)].vstr))) fprintf(stderr, "%s %s\n", ERR_REDECLARED, (yyvsp[(1) - (5)].vstr));
	else fprintf(stderr, "erro. posição %d não existe no array %s\n",(yyvsp[(3) - (5)].vint),(yyvsp[(1) - (5)].vstr));
	}
    break;

  case 31:

/* Line 1806 of yacc.c  */
#line 208 "fruitjuice.y"
    {fprintf(f,"STOREN\n");}
    break;

  case 32:

/* Line 1806 of yacc.c  */
#line 209 "fruitjuice.y"
    {
	if(containsValue((yyvsp[(3) - (4)].vstr))) {
		fprintf(f,"READ\n");
		fprintf(f,"ATOI\n");
		fprintf(f,"STOREG %d\n",findEnderecoV((yyvsp[(3) - (4)].vstr)));
	} else printf("ERRO : Variavel %s não declarada\n",(yyvsp[(3) - (4)].vstr));}
    break;

  case 33:

/* Line 1806 of yacc.c  */
#line 215 "fruitjuice.y"
    {
	if(containsValue((yyvsp[(3) - (7)].vstr)) && validPos((yyvsp[(3) - (7)].vstr),(yyvsp[(5) - (7)].vint))){
		fprintf(f,"PUSHGP\n");
		fprintf(f,"PUSHI %d\n",findEnderecoA((yyvsp[(3) - (7)].vstr),(yyvsp[(5) - (7)].vint)));
		fprintf(f,"PADD\n");
		fprintf(f,"PUSHI %d\n",(yyvsp[(5) - (7)].vint));
		fprintf(f,"READ\n");
		fprintf(f,"ATOI\n");
		fprintf(f,"STOREN\n");}
	else if(!containsValue((yyvsp[(3) - (7)].vstr)))
		printf("ERRO : Variável %s não declarada\n",(yyvsp[(3) - (7)].vstr));
	else
		printf("ERRO : Posição %d não existe no array %s\n",(yyvsp[(5) - (7)].vint),(yyvsp[(3) - (7)].vstr));
	}
    break;

  case 34:

/* Line 1806 of yacc.c  */
#line 230 "fruitjuice.y"
    { fprintf(f,"\n\\\\if then else\n"); insertCond(); }
    break;

  case 35:

/* Line 1806 of yacc.c  */
#line 231 "fruitjuice.y"
    { fprintf(f,"JZ senao%d \n",getHead());}
    break;

  case 36:

/* Line 1806 of yacc.c  */
#line 232 "fruitjuice.y"
    { fprintf(f,"JUMP fse%d \n",getHead());
		   fprintf(f,"senao%d: NOP \n",getHead()); }
    break;

  case 37:

/* Line 1806 of yacc.c  */
#line 234 "fruitjuice.y"
    {
		   fprintf(f,"fse%d: NOP \n",getHead());
		   removeHead();
	   }
    break;

  case 38:

/* Line 1806 of yacc.c  */
#line 238 "fruitjuice.y"
    {fprintf(f,"\n\\\\if then else\n"); insertCond();}
    break;

  case 39:

/* Line 1806 of yacc.c  */
#line 239 "fruitjuice.y"
    { fprintf(f,"JZ senao%d \n",getHead());}
    break;

  case 40:

/* Line 1806 of yacc.c  */
#line 240 "fruitjuice.y"
    { fprintf(f,"JUMP fse%d \n",getHead());
			fprintf(f,"senao%d: NOP \n",getHead()); removeHead(); }
    break;

  case 41:

/* Line 1806 of yacc.c  */
#line 243 "fruitjuice.y"
    {fprintf(f,"INF\n");}
    break;

  case 42:

/* Line 1806 of yacc.c  */
#line 244 "fruitjuice.y"
    {fprintf(f,"INFEQ\n");}
    break;

  case 43:

/* Line 1806 of yacc.c  */
#line 245 "fruitjuice.y"
    {fprintf(f,"SUP\n");}
    break;

  case 44:

/* Line 1806 of yacc.c  */
#line 246 "fruitjuice.y"
    {fprintf(f,"SUPEQ\n");}
    break;

  case 45:

/* Line 1806 of yacc.c  */
#line 247 "fruitjuice.y"
    {fprintf(f,"EQUAL\n");}
    break;

  case 46:

/* Line 1806 of yacc.c  */
#line 248 "fruitjuice.y"
    {fprintf(f,"NOT EQUAL\n");}
    break;

  case 47:

/* Line 1806 of yacc.c  */
#line 249 "fruitjuice.y"
    {fprintf(f,"NOT\n");}
    break;

  case 48:

/* Line 1806 of yacc.c  */
#line 251 "fruitjuice.y"
    { fprintf(f,"\n\\\\while\n");
	        fprintf(f,"ciclo%d: NOP \n",ciclo);
	      }
    break;

  case 49:

/* Line 1806 of yacc.c  */
#line 253 "fruitjuice.y"
    {
				fprintf(f,"JZ fciclo%d \n",fciclo);
			}
    break;

  case 50:

/* Line 1806 of yacc.c  */
#line 256 "fruitjuice.y"
    {
				fprintf(f,"JUMP ciclo%d \n",ciclo);
				ciclo++;
				fprintf(f,"fciclo%d: NOP \n",fciclo);
				fciclo++;
			}
    break;

  case 51:

/* Line 1806 of yacc.c  */
#line 262 "fruitjuice.y"
    { fprintf(f,"\n\\\\for\n");
			fprintf(f,"ciclo%d: NOP \n",ciclo);
		}
    break;

  case 52:

/* Line 1806 of yacc.c  */
#line 264 "fruitjuice.y"
    {
			fprintf(f,"JZ fciclo%d \n",fciclo);
		}
    break;

  case 53:

/* Line 1806 of yacc.c  */
#line 266 "fruitjuice.y"
    {
			fprintf(f,"JUMP ciclo%d \n",ciclo);
			ciclo++;
			fprintf(f,"fciclo%d: NOP \n",fciclo);
			fciclo++;
		}
    break;

  case 55:

/* Line 1806 of yacc.c  */
#line 274 "fruitjuice.y"
    {
		switch((yyvsp[(2) - (3)].vchar)){
			case '+' :
				fprintf(f,"ADD\n");
				break;
			case '-' :
				fprintf(f,"SUB\n");
				break;
			case '*' :
				fprintf(f,"MUL\n");
				break;
			case '/' :
				// Verificar tentativa de divisão por 0
				if((yyvsp[(3) - (3)].vint)==0){
					fprintf(stderr, "%s\n", ERR_DIVISION_BY_0);
				}
				else{
				   fprintf(f,"DIV\n");
				}
				break;
			case '%' :
				fprintf(f,"MOD\n");
				break;
			case '|' :
				fprintf(f,"OR\n");
				break;
			case '&' :
				fprintf(f,"AND\n");
				break;
		}
	   }
    break;

  case 56:

/* Line 1806 of yacc.c  */
#line 306 "fruitjuice.y"
    { (yyval.vint)=(yyvsp[(1) - (1)].vint);fprintf(f,"PUSHI %d\n",(yyvsp[(1) - (1)].vint)); }
    break;

  case 57:

/* Line 1806 of yacc.c  */
#line 307 "fruitjuice.y"
    {
		   if(containsValue((yyvsp[(1) - (1)].vstr))){
			   fprintf(f,"PUSHG %d\n",findEnderecoV((yyvsp[(1) - (1)].vstr)));
		   } else printf("ERRO : Variável %s não declarada\n",(yyvsp[(1) - (1)].vstr));}
    break;

  case 58:

/* Line 1806 of yacc.c  */
#line 311 "fruitjuice.y"
    {
		   if(containsValue((yyvsp[(1) - (4)].vstr)) && validPos((yyvsp[(1) - (4)].vstr),(yyvsp[(3) - (4)].vint))){
			   fprintf(f,"PUSHGP\n");
			   fprintf(f,"PUSHI %d\n",findEnderecoA((yyvsp[(1) - (4)].vstr),(yyvsp[(3) - (4)].vint)));
			   fprintf(f,"PADD\n");
			   fprintf(f,"PUSHI %d\n",(yyvsp[(3) - (4)].vint));
			   fprintf(f,"LOADN\n");
		   } else if(!containsValue((yyvsp[(1) - (4)].vstr)))
			   printf("ERRO : Variável %s não declarada\n",(yyvsp[(1) - (4)].vstr));
		   else
		       printf("ERRO : Posição %d não existe no array %s\n",(yyvsp[(3) - (4)].vint),(yyvsp[(1) - (4)].vstr));
	   }
    break;

  case 59:

/* Line 1806 of yacc.c  */
#line 324 "fruitjuice.y"
    {(yyval.vchar)=(yyvsp[(1) - (1)].vchar);}
    break;

  case 60:

/* Line 1806 of yacc.c  */
#line 325 "fruitjuice.y"
    {(yyval.vchar)=(yyvsp[(1) - (1)].vchar);}
    break;

  case 61:

/* Line 1806 of yacc.c  */
#line 327 "fruitjuice.y"
    {(yyval.vchar)='+';}
    break;

  case 62:

/* Line 1806 of yacc.c  */
#line 328 "fruitjuice.y"
    {(yyval.vchar)='-';}
    break;

  case 63:

/* Line 1806 of yacc.c  */
#line 329 "fruitjuice.y"
    {(yyval.vchar)='|';}
    break;

  case 64:

/* Line 1806 of yacc.c  */
#line 331 "fruitjuice.y"
    {(yyval.vchar)='*';}
    break;

  case 65:

/* Line 1806 of yacc.c  */
#line 332 "fruitjuice.y"
    {(yyval.vchar)='/';}
    break;

  case 66:

/* Line 1806 of yacc.c  */
#line 333 "fruitjuice.y"
    {(yyval.vchar)='%';}
    break;

  case 67:

/* Line 1806 of yacc.c  */
#line 334 "fruitjuice.y"
    {(yyval.vchar)='&';}
    break;

  case 68:

/* Line 1806 of yacc.c  */
#line 336 "fruitjuice.y"
    {
			fprintf(f,"PUSHI %d\n",(yyvsp[(3) - (4)].vint));
			fprintf(f,"WRITEI\n");
			fprintf(f,"PUSHS \"\\n\"\n");
			fprintf(f,"WRITES\n");
		}
    break;

  case 69:

/* Line 1806 of yacc.c  */
#line 342 "fruitjuice.y"
    {
			if(varSimple((yyvsp[(3) - (4)].vstr))==1 && containsValue((yyvsp[(3) - (4)].vstr))){
				fprintf(f,"PUSHG %d\n",findEnderecoV((yyvsp[(3) - (4)].vstr)));
				fprintf(f,"WRITEI\n");
				fprintf(f,"PUSHS \"\\n\"\n");
				fprintf(f,"WRITES\n");
			} else if (varSimple((yyvsp[(3) - (4)].vstr))==0 && containsValue((yyvsp[(3) - (4)].vstr))){
				generateCodeA((yyvsp[(3) - (4)].vstr),f);
			} else printf("ERRO : Variável %s não declarada\n",(yyvsp[(3) - (4)].vstr));
		}
    break;

  case 70:

/* Line 1806 of yacc.c  */
#line 352 "fruitjuice.y"
    {
			if(containsValue((yyvsp[(3) - (7)].vstr)) && validPos((yyvsp[(3) - (7)].vstr),(yyvsp[(5) - (7)].vint))){
				fprintf(f,"PUSHGP\n");
				fprintf(f,"PUSHI %d\n",findEnderecoA((yyvsp[(3) - (7)].vstr),(yyvsp[(5) - (7)].vint)));
				fprintf(f,"PADD\n");
				fprintf(f,"PUSHI %d\n",(yyvsp[(5) - (7)].vint));
				fprintf(f,"LOADN\n");
				fprintf(f,"WRITEI\n");
				fprintf(f,"PUSHS \"\\n\"\n");
				fprintf(f,"WRITES\n");
			} else if(!containsValue((yyvsp[(3) - (7)].vstr)))
				printf("ERRO : Variável %s não declarada\n",(yyvsp[(3) - (7)].vstr));
			else printf("ERRO : Posição %d não existe no array %s\n",(yyvsp[(5) - (7)].vint),(yyvsp[(3) - (7)].vstr));
		}
    break;



/* Line 1806 of yacc.c  */
#line 2120 "y.tab.c"
      default: break;
    }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;

  /* Now `shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*------------------------------------.
| yyerrlab -- here on detecting error |
`------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYEMPTY : YYTRANSLATE (yychar);

  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
# define YYSYNTAX_ERROR yysyntax_error (&yymsg_alloc, &yymsg, \
                                        yyssp, yytoken)
      {
        char const *yymsgp = YY_("syntax error");
        int yysyntax_error_status;
        yysyntax_error_status = YYSYNTAX_ERROR;
        if (yysyntax_error_status == 0)
          yymsgp = yymsg;
        else if (yysyntax_error_status == 1)
          {
            if (yymsg != yymsgbuf)
              YYSTACK_FREE (yymsg);
            yymsg = (char *) YYSTACK_ALLOC (yymsg_alloc);
            if (!yymsg)
              {
                yymsg = yymsgbuf;
                yymsg_alloc = sizeof yymsgbuf;
                yysyntax_error_status = 2;
              }
            else
              {
                yysyntax_error_status = YYSYNTAX_ERROR;
                yymsgp = yymsg;
              }
          }
        yyerror (yymsgp);
        if (yysyntax_error_status == 2)
          goto yyexhaustedlab;
      }
# undef YYSYNTAX_ERROR
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
	 error, discard it.  */

      if (yychar <= YYEOF)
	{
	  /* Return failure if at end of input.  */
	  if (yychar == YYEOF)
	    YYABORT;
	}
      else
	{
	  yydestruct ("Error: discarding",
		      yytoken, &yylval);
	  yychar = YYEMPTY;
	}
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  /* Do not reclaim the symbols of the rule which action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;	/* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
	{
	  yyn += YYTERROR;
	  if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
	    {
	      yyn = yytable[yyn];
	      if (0 < yyn)
		break;
	    }
	}

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
	YYABORT;


      yydestruct ("Error: popping",
		  yystos[yystate], yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  *++yyvsp = yylval;


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#if !defined(yyoverflow) || YYERROR_VERBOSE
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval);
    }
  /* Do not reclaim the symbols of the rule which action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
		  yystos[*yyssp], yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  /* Make sure YYID is used.  */
  return YYID (yyresult);
}



/* Line 2067 of yacc.c  */
#line 368 "fruitjuice.y"


int yyerror(char *s)
{
   fprintf(stderr, "ERRO: %s\n", s);
   return 0;
}

int main()
{
   f = fopen("assembly.vm", "w");

   yyparse();

   fprintf(f,"STOP\n");
   fclose(f);

   printHash();

   orderHashEnd();
   system("pdflatex stack.tex");
   system("open stack.pdf");
   system("clear");

   return(0);
}


